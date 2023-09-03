package zw.org.nmr.limsehr.service.subscriber;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import jakarta.transaction.Transactional;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Task;
import org.hl7.fhir.r4.model.codesystems.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.service.subscriber.resolver.LaboratoryRequestResolver;
import zw.org.nmr.limsehr.service.subscriber.resolver.PatientResolver;

@Service
@Transactional
public class RequestedOrders {

    private final Logger log = LoggerFactory.getLogger(RequestedOrders.class);

    @Value("${hapi.fhir.baseUrl}")
    String hapiFhirBaseUrl;

    @Value("${hapi.fhir.username}")
    String hapiFhirUsername;

    @Value("${hapi.fhir.password}")
    String hapiFhirPassword;

    PatientResolver patientResolver;
    LaboratoryRequestResolver laboratoryRequestResolver;

    public RequestedOrders(PatientResolver patientResolver, LaboratoryRequestResolver laboratoryRequestResolver) {
        this.patientResolver = patientResolver;
        this.laboratoryRequestResolver = laboratoryRequestResolver;
    }

    public static void checkNotNull(Object obj, String message) {
        if (obj == null) {
            throw new RuntimeException(message);
        }
    }

    //    @Scheduled(fixedRate = 2000)
    public void getRequestedOrders() {
        Patient patient = null;
        Location laboratory = null;
        Location facility = null;
        Encounter encounter = null;
        Specimen specimen = null; //Sample
        ServiceRequest serviceRequest = null; //Test
        Task task = null;

        FhirContext ctx = FhirContext.forR4();
        IGenericClient fhirClient = ctx.newRestfulGenericClient(this.hapiFhirBaseUrl);
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(this.hapiFhirUsername, this.hapiFhirPassword);
        fhirClient.registerInterceptor(authInterceptor);

        // in production you will  want to to look for REQUESTED
        Bundle taskBundle = fhirClient
            .search()
            .forResource(Task.class)
            .where(Task.STATUS.exactly().code(TaskStatus.RECEIVED.toCode()))
            .returnBundle(Bundle.class)
            .execute();

        log.debug("Bundle task bundle response: Total {}", taskBundle.getTotal());

        for (Bundle.BundleEntryComponent thinTask : taskBundle.getEntry()) {
            if (thinTask.getResource() instanceof Task tTask) {
                Bundle bundle = fhirClient
                    .search()
                    .forResource(Task.class)
                    .where(new TokenClientParam("_id").exactly().code(tTask.getId()))
                    .include(Task.INCLUDE_ALL)
                    .returnBundle(Bundle.class)
                    .execute();

                log.debug("Bundle for task {}", tTask.getIdElement().getIdPart());

                // System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));

                for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                    if (entry.hasResource()) {
                        switch (entry.getResource().getResourceType()) {
                            case Task:
                                task = (Task) entry.getResource();
                                break;
                            case Encounter:
                                encounter = (Encounter) entry.getResource();
                                break;
                            case Location:
                                laboratory = (Location) entry.getResource();
                                break;
                            case ServiceRequest:
                                serviceRequest = (ServiceRequest) entry.getResource();
                                break;
                            case Specimen:
                                specimen = (Specimen) entry.getResource();
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

                String taskLaboratoryId = task.getLocation().getReferenceElement().getIdPart();
                bundle =
                    fhirClient
                        .search()
                        .forResource(Location.class)
                        .where(new TokenClientParam("_id").exactly().code(taskLaboratoryId))
                        .include(Task.INCLUDE_ALL)
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

                //Get Specimen (Sample)
                String serviceRequestId = serviceRequest.getIdElement().getIdPart();
                bundle =
                    fhirClient
                        .search()
                        .forResource(ServiceRequest.class)
                        .where(new TokenClientParam("_id").exactly().code(serviceRequestId))
                        .include(Task.INCLUDE_ALL)
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

                // 1. Get Facility
                String encounterId = encounter.getIdElement().getIdPart();
                bundle =
                    fhirClient
                        .search()
                        .forResource(Encounter.class)
                        .where(new TokenClientParam("_id").exactly().code(encounterId))
                        .include(Task.INCLUDE_ALL)
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

                // All required objects must exist checks
                checkNotNull(patient, "Patient not found");
                checkNotNull(serviceRequest, "ServiceRequest not found");
                checkNotNull(task, "Task not found");
                checkNotNull(specimen, "Specimen not found");
                checkNotNull(encounter, "Encounter not found");
                checkNotNull(facility, "Facility not found");
                checkNotNull(laboratory, "Laboratory not found");

                System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(specimen));

                // Resolve Fhir objects to LIMS objects and save
                zw.org.nmr.limsehr.domain.Patient limsPatient = patientResolver.resolveAndSavePatient(patient);
                LaboratoryRequest laboratoryRequest = laboratoryRequestResolver.resolveAndSaveLaboratoryRequest(
                    task,
                    serviceRequest,
                    specimen,
                    limsPatient,
                    laboratory,
                    facility
                );

                // Once Task resolution is complete update task as receiveds
                if (laboratoryRequest != null) {
                    task.setStatus(Task.TaskStatus.RECEIVED);
                    fhirClient.update().resource(task).execute();
                }
            }
        }
    }
}
