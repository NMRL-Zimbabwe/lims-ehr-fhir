package zw.org.nmr.limsehr.service.subscriber.reference;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Task;

public class TaskAssociatedObjects {

    //A Task is Lab Order
    public void getTaskAssociatedObjects(Task task) {
        Patient patient;
        Location laboratory;
        Location facility;
        Encounter encounter = null;
        Specimen specimen; //Sample
        ServiceRequest serviceRequest = null; //Test

        IGenericClient fhirClient = FhirClientUtility.getFhirClient();

        String taskId = task.getIdElement().getIdPart();

        Bundle bundle = fhirClient
            .search()
            .forResource(Task.class)
            .where(new TokenClientParam("_id").exactly().code(taskId))
            .include(Task.INCLUDE_ALL)
            .returnBundle(Bundle.class)
            .execute();

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.hasResource()) {
                switch (entry.getResource().getResourceType()) {
                    case Task:
                        task = (Task) entry.getResource();
                        break;
                    case Encounter:
                        encounter = (Encounter) entry.getResource();
                        break;
                    case ServiceRequest:
                        serviceRequest = (ServiceRequest) entry.getResource();
                        break;
                    case Patient:
                        patient = (Patient) entry.getResource();
                        break;
                    default:
                        break;
                }
            }
        }

        //Get Laboratory
        if (task != null) {
            String taskLaboratoryId = task.getLocation().getReferenceElement().getIdPart();

            bundle =
                fhirClient
                    .search()
                    .forResource(Location.class)
                    .where(new TokenClientParam("_id").exactly().code(taskLaboratoryId))
                    .include(Location.INCLUDE_ALL)
                    .returnBundle(Bundle.class)
                    .execute();

            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                if (entry.hasResource()) {
                    switch (entry.getResource().getResourceType()) {
                        case Location:
                            laboratory = (Location) entry.getResource();
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        //Get Specimen (Sample)
        if (serviceRequest != null) {
            String serviceRequestId = serviceRequest.getIdElement().getIdPart();

            bundle =
                fhirClient
                    .search()
                    .forResource(ServiceRequest.class)
                    .where(new TokenClientParam("_id").exactly().code(serviceRequestId))
                    .include(ServiceRequest.INCLUDE_ALL)
                    .returnBundle(Bundle.class)
                    .execute();

            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                if (entry.hasResource()) {
                    switch (entry.getResource().getResourceType()) {
                        case Specimen:
                            specimen = (Specimen) entry.getResource();
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        //Get Facility
        if (encounter != null) {
            String encounterId = encounter.getIdElement().getIdPart();

            bundle =
                fhirClient
                    .search()
                    .forResource(Encounter.class)
                    .where(new TokenClientParam("_id").exactly().code(encounterId))
                    .include(Encounter.INCLUDE_ALL)
                    .returnBundle(Bundle.class)
                    .execute();

            for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                if (entry.hasResource()) {
                    switch (entry.getResource().getResourceType()) {
                        case Location:
                            facility = (Location) entry.getResource();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
