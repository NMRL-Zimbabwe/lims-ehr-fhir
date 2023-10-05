package zw.org.nmr.limsehr.service.subscriber.resolver;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
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

    public AnalysisResultIssuer(LaboratoryRequestRepository laboratoryRequestRepository) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    @Scheduled(fixedRate = 2000)
    public void issueResult() {
        FhirContext ctx = FhirContext.forR4();
        IGenericClient fhirClient = ctx.newRestfulGenericClient(this.hapiFhirBaseUrl);
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(this.hapiFhirUsername, this.hapiFhirPassword);
        fhirClient.registerInterceptor(authInterceptor);

        List<LaboratoryRequest> labRequests = laboratoryRequestRepository.findByResultIsNotNullAndResultStatusIsNull();

        for (LaboratoryRequest lr : labRequests) {
            Task task = fhirClient.read().resource(Task.class).withId(lr.getMiddlewareAnalysisRequestUuid()).execute();

            //We call this Result Bundle
            List<Resource> fhirResources = new ArrayList<>();

            task.setStatus(Task.TaskStatus.COMPLETED);
            Task.TaskOutputComponent output = new Task.TaskOutputComponent();

            DiagnosticReport diagnosticReport = getDiagnosticReport(task, lr.getLaboratoryRequestId());
            String diagnosticReportId = diagnosticReport.getIdElement().getIdPart();
            Reference diagnosticReportReference = FhirReferenceCreator.getReference(diagnosticReportId, "DiagnosticReport");
            output.setValue(diagnosticReportReference);
            task.setOutput(Collections.singletonList(output));

            // Loop results and add observations
            Observation observation = getObservation(task, lr);

            String observationId = observation.getIdElement().getIdPart();
            Reference observationReference = FhirReferenceCreator.getReference(observationId, "Observation");
            diagnosticReport.setResult(Collections.singletonList(observationReference));

            //NB:: Start with Observations, followed by Diagnostic Reports, and finally Tasks
            fhirResources.add(observation);
            fhirResources.add(diagnosticReport);
            fhirResources.add(task);

            //Save this Result Bundle in the Shared Health Record (SHR):: OpenHIE
            //            FhirResourcesSaver.saveFhirResources(fhirResources);
            fhirClient.update().resource(observation).execute();
            //            fhirClient.update().resource(diagnosticReport).execute();
            //            fhirClient.update().resource(task).execute();
            lr.setResultStatus("SENT_TO_EHR");
            laboratoryRequestRepository.save(lr);
        }
    }

    public static DiagnosticReport getDiagnosticReport(Task task, String labRequestId) {
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        diagnosticReport.setId(labRequestId);
        //This is hard coded for now as an example
        diagnosticReport.setCode(new CodeableConcept(new Coding("http://loinc.org", "22748-8", "")));
        diagnosticReport.setSubject(task.getFor());
        return diagnosticReport;
    }

    //Analysis
    public static Observation getObservation(Task task, LaboratoryRequest labReq) {
        //Note:: Here I am randomly generating the result. In reality this should come from your Lims system.
        Observation observation = new Observation();
        observation.setId(labReq.getLaboratoryRequestId());
        observation.setSubject(task.getFor());
        //Add Test Analysis Code.  //This is hard coded for now as an example
        observation.setCode(new CodeableConcept(new Coding("http://loinc.org", "22748-8", "")));

        StringType stringResult = new StringType();
        stringResult.setValue(labReq.getResult());
        observation.setValue(stringResult);

        // observation.setValue(new Quantity().setValue(Float.parseFloat(labReq.getResult())).setUnit(labReq.getUnit()));
        observation.setLanguage("ENGLISH");

        //        Device device = new Device();
        //        device.setId("abbott");
        //        Reference deviceReference = new Reference(device);
        //
        //        observation.setDevice(deviceReference);
        return observation;
    }
}
