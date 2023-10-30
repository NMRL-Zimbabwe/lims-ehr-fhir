package zw.org.nmr.limsehr.service.subscriber.resolver;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.JobScheduler;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.service.JobSchedulerService;
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

        for (LaboratoryRequest lr : labRequests) {
            Task task = fhirClient.read().resource(Task.class).withId(lr.getMiddlewareAnalysisRequestUuid()).execute();

            ehrAcknoledgements(fhirClient, lr, task);

            lr.setResultStatus(LaboratoryRequestStatus.SENT_RESULT_TO_EHR.toString());
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

            ehrAcknoledgements(fhirClient, lr, task);

            lr.setSentToEhr(LaboratoryRequestStatus.SENT_TO_EHR.toString());

            laboratoryRequestRepository.save(lr);
        }
    }

    private void ehrAcknoledgements(IGenericClient fhirClient, LaboratoryRequest lr, Task task) {
        // We call this Result Bundle
        List<Resource> fhirResources = new ArrayList<>();

        task.setStatus(Task.TaskStatus.COMPLETED);
        Task.TaskOutputComponent output = new Task.TaskOutputComponent();

        DiagnosticReport diagnosticReport = getDiagnosticReport(task, lr.getLaboratoryRequestId());
        String diagnosticReportId = diagnosticReport.getIdElement().getIdPart();
        Reference diagnosticReportReference = FhirReferenceCreator.getReference(diagnosticReportId, "DiagnosticReport");
        output.setValue(diagnosticReportReference);
        task.setOutput(Collections.singletonList(output));

        // Loop results and add observations
        Observation observation = getObservation(fhirClient, task, lr);

        String observationId = observation.getIdElement().getIdPart();
        Reference observationReference = FhirReferenceCreator.getReference(observationId, "Observation");
        diagnosticReport.setResult(Collections.singletonList(observationReference));

        // NB:: Start with Observations, followed by Diagnostic Reports, and finally
        // Tasks
        fhirResources.add(observation);
        fhirResources.add(diagnosticReport);
        fhirResources.add(task);

        // Save this Result Bundle in the Shared Health Record (SHR):: OpenHIE
        fhirClient.update().resource(observation).execute();
        // Send Diagnostic report
        fhirClient.update().resource(diagnosticReport).execute();
        // update task status to completed
        fhirClient.update().resource(task).execute();
    }

    public static DiagnosticReport getDiagnosticReport(Task task, String labRequestId) {
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        diagnosticReport.setId(labRequestId);
        // This is hard coded for now as an example
        diagnosticReport.setCode(new CodeableConcept(new Coding("http://loinc.org", "22748-8", "")));
        // diagnosticReport.setIssued(null)
        diagnosticReport.setSubject(task.getFor());
        return diagnosticReport;
    }

    // Analysis
    public static Observation getObservation(IGenericClient clt, Task task, LaboratoryRequest labReq) {
        // Note:: Here I am randomly generating the result. In reality this should come
        // from your Lims system.
        Observation observation = new Observation();
        observation.setId(labReq.getLaboratoryRequestId());
        observation.setSubject(task.getFor());
        // Add Test Analysis Code. //This is hard coded for now as an example
        observation.setCode(new CodeableConcept(new Coding("urn:impilo:code", labReq.getTestId(), "")));

        StringType stringResult = new StringType();
        stringResult.setValue(labReq.getResult());
        observation.setValue(stringResult);

        int intCriticalResult = 0;
        try {
            if (labReq.getResult() != null) {
                // remove non-numeric characters from a Java string e.g <>
                intCriticalResult = Integer.parseInt(labReq.getResult().replaceAll("[^0-9]", ""));

                System.out.println("The string is an integer: " + intCriticalResult);
            }
        } catch (NumberFormatException e) {
            System.out.println("The string is not an integer.");
        }

        if (intCriticalResult >= 1000 || labReq.isBreastfeeding() || labReq.isPregnant()) {
            CodeableConcept codeableConcept = new CodeableConcept();
            codeableConcept.setText("CRITICAL_RESULT");
            Coding coding = new Coding();
            coding.setSystem("urn:lims:code");
            coding.setCode(String.valueOf(labReq.getResult()));
            coding.setDisplay(String.valueOf(labReq.getResult()));
            codeableConcept.addCoding(coding);
            observation.addInterpretation(codeableConcept);
        }

        CodeableConcept ccResultInterpretation = new CodeableConcept();
        ccResultInterpretation.setText("RESULT_INTERPRETATION");
        Coding coding = new Coding();
        coding.setSystem("urn:lims:code");
        coding.setCode("VL");
        coding.setDisplay(
            "1. VL <= 1000 copies/ml: Continue on current Regimen. 2. VL > 1000 copies/ml: Clinical and Counseling action Required"
        );
        ccResultInterpretation.addCoding(coding);
        observation.addInterpretation(ccResultInterpretation);

        // observation.setValue(new
        // Quantity().setValue(Float.parseFloat(labReq.getResult())).setUnit(labReq.getUnit()));
        observation.setLanguage("ENGLISH");

        Device device = new Device();
        String InstrumentName = labReq.getInstrument() != null ? labReq.getInstrument().replace(" ", "") : "None";

        if (InstrumentName.length() == 0) {
            InstrumentName = "None";
        }
        device.setId(InstrumentName);
        // Create a device name
        StringType deviceName = new StringType();
        deviceName.setValue(InstrumentName);
        Extension deviceNameExtension = new Extension();
        deviceNameExtension.setUrl("url:lims:device");
        deviceNameExtension.setValue(deviceName);
        device.addExtension(deviceNameExtension);
        clt.update().resource(device).execute();
        //
        Reference deviceReference = FhirReferenceCreator.getReference(InstrumentName, "Device");
        observation.setDevice(deviceReference);
        return observation;
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
