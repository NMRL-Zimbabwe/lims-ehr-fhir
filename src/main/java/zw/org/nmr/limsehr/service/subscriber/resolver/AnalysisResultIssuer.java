package zw.org.nmr.limsehr.service.subscriber.resolver;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import java.time.ZoneId;
import java.util.*;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.JobScheduler;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.service.JobSchedulerService;
import zw.org.nmr.limsehr.service.enums.FhirLimsCodes;
import zw.org.nmr.limsehr.service.enums.LaboratoryRequestStatus;
import zw.org.nmr.limsehr.service.subscriber.reference.FhirReferenceCreator;

@Service
public class AnalysisResultIssuer {

    @Value("${hapi.fhir.baseUrl}")
    String hapiFhirBaseUrl;

    @Value("${hapi.fhir.username}")
    String hapiFhirUsername;

    @Value("${hapi.fhir.password}")
    String hapiFhirPassword;

    LaboratoryRequestRepository laboratoryRequestRepository;

    JobSchedulerService jobSchedulerService;

    public AnalysisResultIssuer(LaboratoryRequestRepository laboratoryRequestRepository, JobSchedulerService jobSchedulerService) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
        this.jobSchedulerService = jobSchedulerService;
    }

    // 60000 represents a minute
    @Scheduled(fixedRate = 60000 * 1) // or @Scheduled(cron = "0 */5 * * * *", zone = "Africa/Harare")
    public void issueResult() {
        Optional<JobScheduler> isSchedule = jobSchedulerService.resolverScheduled("SEND_RESULTS_TO_EHR");
        if (isSchedule.isEmpty()) {
            return;
        }
        if (isSchedule.orElseThrow().isInActive()) {
            return;
        }

        FhirContext ctx = FhirContext.forR4();
        IGenericClient fhirClient = ctx.newRestfulGenericClient(this.hapiFhirBaseUrl);
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(this.hapiFhirUsername, this.hapiFhirPassword);
        fhirClient.registerInterceptor(authInterceptor);

        List<LaboratoryRequest> labRequests = laboratoryRequestRepository.findByResultIsNotNullAndResultStatusIsNull();
        labRequests = labRequests.stream().filter(lr -> !lr.getResult().isBlank()).toList();

        for (LaboratoryRequest lr : labRequests) {
            Task task = fhirClient.read().resource(Task.class).withId(lr.getMiddlewareAnalysisRequestUuid()).execute();

            if (lr.getStatus() == null) { // !IMPORTANT
                continue;
            }

            String status = lr.getStatus().toLowerCase();
            if (status.equals("rejected")) {
                handleRejected(fhirClient, task, lr);
            } else {
                ehrAcknowledgements(fhirClient, lr, task);
            }

            if (status.equals("published") || status.equals("rejected")) {
                lr.setResultStatus(LaboratoryRequestStatus.SENT_RESULT_TO_EHR.toString());
            }

            lr.setSentToEhr(LaboratoryRequestStatus.SENT_TO_EHR.toString());
            laboratoryRequestRepository.save(lr);
        }
    }

    @Scheduled(fixedRate = 60000 * 1) // or @Scheduled(cron = "0 */5 * * * *", zone = "Africa/Harare")
    public void issueAcknowledgement() {
        Optional<JobScheduler> isSchedule = jobSchedulerService.resolverScheduled("SEND_RESULTS_TO_EHR");
        if (isSchedule.isEmpty()) {
            return;
        }
        if (isSchedule.orElseThrow().isInActive()) {
            return;
        }

        FhirContext ctx = FhirContext.forR4();
        IGenericClient fhirClient = ctx.newRestfulGenericClient(this.hapiFhirBaseUrl);
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(this.hapiFhirUsername, this.hapiFhirPassword);
        fhirClient.registerInterceptor(authInterceptor);

        List<LaboratoryRequest> labRequests = laboratoryRequestRepository.findByLabReferenceSampleIdIsNotNullAndSentToEhrIsNull();

        for (LaboratoryRequest lr : labRequests) {
            Task task = fhirClient.read().resource(Task.class).withId(lr.getMiddlewareAnalysisRequestUuid()).execute();
            ehrAcknowledgements(fhirClient, lr, task);
            lr.setSentToEhr(LaboratoryRequestStatus.SENT_TO_EHR.toString());
            laboratoryRequestRepository.save(lr);
        }
    }

    private void ehrAcknowledgements(IGenericClient fhirClient, LaboratoryRequest lr, Task task) {
        switch (lr.getStatus().toLowerCase()) {
            case "published":
                task.setStatus(Task.TaskStatus.COMPLETED);
                break;
            case "sample_due":
                task.setStatus(Task.TaskStatus.ONHOLD);
                break;
            case "registered", "received":
                task.setStatus(Task.TaskStatus.ACCEPTED);
                break;
            default: // "to_be_verified", "verified"
                task.setStatus(Task.TaskStatus.INPROGRESS);
        }

        Task.TaskOutputComponent output = new Task.TaskOutputComponent();

        DiagnosticReport diagnosticReport = getDiagnosticReport(task, lr);
        String diagnosticReportId = diagnosticReport.getIdElement().getIdPart();
        Reference diagnosticReportReference = FhirReferenceCreator.getReference(diagnosticReportId, "DiagnosticReport");
        output.setValue(diagnosticReportReference);
        task.setOutput(Collections.singletonList(output));

        // Loop results and add observations
        Observation observation = getObservation(fhirClient, task, lr);
        String observationId = observation.getIdElement().getIdPart();
        Reference observationReference = FhirReferenceCreator.getReference(observationId, "Observation");
        diagnosticReport.setResult(Collections.singletonList(observationReference));

        // Save this Result Bundle in the Shared Health Record (SHR):: OpenHIE
        fhirClient.update().resource(observation).execute();
        // Send Diagnostic report
        fhirClient.update().resource(diagnosticReport).execute();
        // update task status to completed
        fhirClient.update().resource(task).execute();
    }

    public static DiagnosticReport getDiagnosticReport(Task task, LaboratoryRequest labReq) {
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        diagnosticReport.setLanguage("ENGLISH");
        if (labReq.getStatus().equalsIgnoreCase("published")) {
            diagnosticReport.setStatus(DiagnosticReport.DiagnosticReportStatus.FINAL);
        } else {
            diagnosticReport.setStatus(DiagnosticReport.DiagnosticReportStatus.PRELIMINARY);
        }
        diagnosticReport.setId(labReq.getLaboratoryRequestId());
        diagnosticReport.setCode(
            new CodeableConcept(new Coding(FhirLimsCodes.IMPILO_CODE_URN.getValue(), labReq.getTestId(), labReq.getTestName()))
        );
        diagnosticReport.setSubject(task.getFor());
        if (labReq.getDateTested() != null) {
            diagnosticReport.setEffective(new DateTimeType(String.valueOf(labReq.getDateTested())));
        }
        if (labReq.getDatePublished() != null) {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date dt = Date.from(labReq.getDatePublished().atZone(defaultZoneId).toInstant());
            diagnosticReport.setIssued(dt);
        }
        return diagnosticReport;
    }

    // Analysis
    public static Observation getObservation(IGenericClient clt, Task task, LaboratoryRequest labReq) {
        Observation observation = new Observation();
        observation.setLanguage("ENGLISH");
        if (labReq.getStatus().equalsIgnoreCase("published")) {
            observation.setStatus(Observation.ObservationStatus.FINAL);
        } else {
            observation.setStatus(Observation.ObservationStatus.PRELIMINARY);
        }
        observation.setStatus(Observation.ObservationStatus.FINAL);
        observation.setId(labReq.getLaboratoryRequestId());
        observation.setSubject(task.getFor());
        observation.setCode(
            new CodeableConcept(new Coding(FhirLimsCodes.IMPILO_CODE_URN.getValue(), labReq.getTestId(), labReq.getTestName()))
        );

        // Set Result based on type
        StringType stringResult = new StringType();
        stringResult.setValue(labReq.getResult());
        observation.setValue(stringResult);

        // handle critical results if its critical
        handleCriticalResults(task, observation, labReq);
        // addResultRemark
        addResultRemark(observation, labReq);
        // add interpretations
        addResultInterpretation(observation, labReq);
        // add instrument
        addInstrument(clt, observation, labReq);
        // addMethod
        addMethod(observation, labReq);
        // TODO: Add performer

        return observation;
    }

    private static void addMethod(Observation observation, LaboratoryRequest labReq) {
        if (labReq.getMethod() == null || labReq.getMethod().isBlank()) {
            return;
        }
        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.setText(FhirLimsCodes.TEST_METHOD.toString());
        Coding coding = new Coding();
        coding.setSystem(FhirLimsCodes.LIMS_CODE_URN.getValue());
        coding.setCode(FhirLimsCodes.TEST_METHOD_CODE.toString());
        coding.setDisplay(labReq.getMethod());
        codeableConcept.addCoding(coding);
        observation.addInterpretation(codeableConcept);
    }

    private static void addInstrument(IGenericClient clt, Observation observation, LaboratoryRequest labReq) {
        if (labReq.getInstrument() == null || labReq.getInstrument().isBlank()) {
            return;
        }
        String instrument = labReq.getInstrument().replace(" ", "-");
        Device device = new Device();
        device.setId(instrument.toLowerCase());
        StringType deviceName = new StringType();
        deviceName.setValue(instrument);
        Extension deviceNameExtension = new Extension();
        deviceNameExtension.setUrl(FhirLimsCodes.LIMS_DEVICE_URN.getValue());
        deviceNameExtension.setValue(deviceName);
        device.addExtension(deviceNameExtension);
        // !! important !!
        clt.update().resource(device).execute();
        // then
        Reference deviceReference = FhirReferenceCreator.getReference(instrument, "Device");
        observation.setDevice(deviceReference);
    }

    private static void handleCriticalResults(Task task, Observation observation, LaboratoryRequest labReq) {
        int intCriticalResult = 0;
        try {
            if (labReq.getResult() != null) {
                // remove non-numeric characters from a Java string e.g <>
                intCriticalResult = Integer.parseInt(labReq.getResult().replaceAll("[^0-9]", ""));
            }
        } catch (NumberFormatException e) {
            //
        }

        if (intCriticalResult >= 1000 || labReq.isBreastfeeding() || labReq.isPregnant()) {
            addCriticalResultToTask(task);
            addCriticalResultInterpretation(observation, labReq);
        }
    }

    private static void addCriticalResultInterpretation(Observation observation, LaboratoryRequest lr) {
        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.setText(FhirLimsCodes.CRITICAL_RESULT.toString());
        Coding coding = new Coding();
        coding.setSystem(FhirLimsCodes.LIMS_CODE_URN.getValue());
        coding.setCode(FhirLimsCodes.CRITICAL_RESULT_CODE.toString());
        coding.setDisplay(String.valueOf(true));
        codeableConcept.addCoding(coding);
        observation.addInterpretation(codeableConcept);
    }

    private static void addCriticalResultToTask(Task task) {
        Extension extension = new Extension();
        extension.setId(FhirLimsCodes.URN_TASK_CRITICAL_RESULT_ID_PREFIX.getValue() + "-" + task.getIdElement().getIdPart());
        extension.setUrl(FhirLimsCodes.URN_TASK_CRITICAL_RESULT.getValue());
        extension.setValue(new BooleanType(true));
        task.addExtension(extension);
    }

    private static void addResultInterpretation(Observation observation, LaboratoryRequest labReq) {
        if (!labReq.getTestName().toLowerCase().contains("viral")) {
            return;
        }
        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.setText(FhirLimsCodes.RESULT_INTERPRETATION.toString());
        Coding coding = new Coding();
        coding.setSystem(FhirLimsCodes.LIMS_CODE_URN.getValue());
        coding.setCode(FhirLimsCodes.RESULT_INTERPRETATION_CODE.getValue());
        // TODO: standardise, add new column called comment/intepretation to lims_test data table and get this dynamically
        coding.setDisplay(
            "1. VL <= 1000 copies/ml: Continue on current Regimen. 2. VL > 1000 copies/ml: Clinical and Counseling action Required"
        );
        codeableConcept.addCoding(coding);
        observation.addInterpretation(codeableConcept);
    }

    private static void addResultRemark(Observation observation, LaboratoryRequest labReq) {
        if (labReq.getRemarks() == null || labReq.getRemarks().isBlank()) {
            return;
        }
        Annotation annotation = new Annotation();
        annotation.setText(labReq.getRemarks());
        observation.addNote(annotation);
    }

    public static void handleRejected(IGenericClient clt, Task task, LaboratoryRequest lr) {
        task.setStatus(Task.TaskStatus.REJECTED);
        Extension statusReasonsExtension = new Extension(FhirLimsCodes.STATUS_REASONS_EXTENSION_URL.getValue());

        List<String> rejectionReasons = Arrays.stream(lr.getResult().split(",")).toList();

        for (String rr : rejectionReasons) {
            Extension statusReasonExtension = getRejectionReasonExtension(rr);
            statusReasonsExtension.addExtension(statusReasonExtension);
        }
        task.addExtension(statusReasonsExtension);
        //
        clt.update().resource(task).execute();
    }

    private static Extension getRejectionReasonExtension(String rejectionReason) {
        CodeableConcept concept = new CodeableConcept();
        Coding coding = new Coding();
        coding.setSystem(FhirLimsCodes.LIMS_CODE_URN.getValue());
        coding.setCode(FhirLimsCodes.REJECTION_REASON_CODE.getValue());
        coding.setDisplay(rejectionReason);
        concept.addCoding(coding);
        Extension extension = new Extension();
        // TODO: standardise rejection reasons in a table
        extension.setUrl(FhirLimsCodes.STATUS_REASONS_EXTENSION_URL.getValue() + "#" + UUID.randomUUID());
        extension.setValue(concept);
        return extension;
    }
}
//Quantity Value
//   if(resultType.equals("quantityType"){
//       observation.setValue(new Quantity().setValue(30.78).setUnit("UL/I2"));
//       }
//
//       //String value
//       if(resultType.equals("stringType"){
//       observation.setValue(new StringType().setValue("My String value"));
//       }
//
//       //Boolean value
//       if(resultType.equals("booleanType"){
//       observation.setValue(new BooleanType(true));
//       }
//
//       //Integer value
//       if(resultType.equals("integerType"){
//       observation.setValue(new IntegerType().setValue(100));
//       }
//
//
//       //Ratio value
//       if(resultType.equals("ratioType"){
//       observation.setValue(new Ratio().setNumerator(new Quantity(50)).setDenominator(new Quantity(123.45)));
//       }
//
//       //Period value
//       if(resultType.equals("periodType"){
//       observation.setValue(new Period().setStart(new Date()).setEnd(new Date()));
//       }
