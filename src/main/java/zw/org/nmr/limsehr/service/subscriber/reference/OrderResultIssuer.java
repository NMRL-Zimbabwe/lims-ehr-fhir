package zw.org.nmr.limsehr.service.subscriber.reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.Task;

public class OrderResultIssuer {

    private OrderResultIssuer() {}

    public static void issueResult(Task task) {
        //We call this Result Bundle
        List<Resource> fhirResources = new ArrayList<>();

        task.setStatus(Task.TaskStatus.COMPLETED);
        Task.TaskOutputComponent output = new Task.TaskOutputComponent();

        DiagnosticReport diagnosticReport = getDiagnosticReport(task);
        String diagnosticReportId = diagnosticReport.getIdElement().getIdPart();
        Reference diagnosticReportReference = FhirReferenceCreator.getReference(diagnosticReportId, "DiagnosticReport");
        output.setValue(diagnosticReportReference);
        task.setOutput(Collections.singletonList(output));

        Observation observation = getObservation(task);

        String observationId = observation.getIdElement().getIdPart();
        Reference observationReference = FhirReferenceCreator.getReference(observationId, "Observation");
        diagnosticReport.setResult(Collections.singletonList(observationReference));

        //NB:: Start with Observations, followed by Diagnostic Reports, and finally Tasks
        fhirResources.add(observation);
        fhirResources.add(diagnosticReport);
        fhirResources.add(task);

        //Save this Result Bundle in the Shared Health Record (SHR):: OpenHIE
        FhirResourcesSaver.saveFhirResources(fhirResources);
    }

    public static DiagnosticReport getDiagnosticReport(Task task) {
        DiagnosticReport diagnosticReport = new DiagnosticReport();
        diagnosticReport.setId(UUID.randomUUID().toString());
        //This is hard coded for now as an example
        diagnosticReport.setCode(new CodeableConcept(new Coding("http://loinc.org", "22748-8", "")));
        diagnosticReport.setSubject(task.getFor());
        return diagnosticReport;
    }

    //Analysis
    public static Observation getObservation(Task task) {
        //Note:: Here I am randomly generating the result. In reality this should come from your Lims system.
        double result = Math.random() * 500;

        Observation observation = new Observation();
        observation.setId(UUID.randomUUID().toString());
        observation.setSubject(task.getFor());
        //Add Test Analysis Code.  //This is hard coded for now as an example
        observation.setCode(new CodeableConcept(new Coding("http://loinc.org", "22748-8", "")));
        observation.setValue(new Quantity().setValue(result).setUnit("UI/L"));
        return observation;
    }
}
