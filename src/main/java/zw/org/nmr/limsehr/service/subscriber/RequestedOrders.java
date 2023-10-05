package zw.org.nmr.limsehr.service.subscriber;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import ca.uhn.fhir.rest.gclient.TokenClientParam;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Location;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Task;
import org.hl7.fhir.r4.model.codesystems.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.JobScheduler;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.service.JobSchedulerService;
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

    JobSchedulerService jobSchedulerService;

    public RequestedOrders(
        PatientResolver patientResolver,
        LaboratoryRequestResolver laboratoryRequestResolver,
        JobSchedulerService jobSchedulerService
    ) {
        this.patientResolver = patientResolver;
        this.laboratoryRequestResolver = laboratoryRequestResolver;
        this.jobSchedulerService = jobSchedulerService;
    }

    public static void checkNotNull(IBaseResource obj, String message, FhirContext ctx) {
        //   System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(obj));
        if (obj == null) {
            throw new RuntimeException(message);
        }
    }

    // 60000 represents a minute
    @Scheduled(fixedRate = 60000 * 1) // or @Scheduled(cron = "0 */5 * * * *", zone = "Africa/Harare")
    public void getRequestedOrders() {
        Optional<JobScheduler> isSchedule = jobSchedulerService.resolverScheduled("GET_REQUESTED_ORDERS");
        if (isSchedule.isEmpty()) {
            return;
        }
        if (isSchedule.get().isInActive()) {
            return;
        }

        Patient patient = null;
        Location laboratory = null;
        Location facility = null;
        Organization organisation = null;
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
            .where(Task.STATUS.exactly().code(TaskStatus.REQUESTED.toCode()))
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

                // Get Organisation
                String orgId = patient.getManagingOrganization().getReferenceElement().getIdPart();
                bundle =
                    fhirClient
                        .search()
                        .forResource(Organization.class)
                        .where(new TokenClientParam("_id").exactly().code(orgId))
                        .include(Task.INCLUDE_ALL)
                        .returnBundle(Bundle.class)
                        .execute();
                for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                    if (entry.hasResource()) {
                        switch (entry.getResource().getResourceType()) {
                            case Organization:
                                organisation = (Organization) entry.getResource();
                                break;
                            default:
                                break;
                        }
                    }
                }

                // All required objects must exist checks
                checkNotNull(patient, "Patient not found", ctx);
                checkNotNull(organisation, "Managing organisation not found", ctx);
                checkNotNull(serviceRequest, "ServiceRequest not found", ctx);
                checkNotNull(task, "Task not found", ctx);
                checkNotNull(specimen, "Specimen not found", ctx);
                checkNotNull(encounter, "Encounter not found", ctx);
                checkNotNull(facility, "Facility not found", ctx);
                checkNotNull(laboratory, "Laboratory not found", ctx);

                // Resolve Fhir objects to LIMS objects and save
                zw.org.nmr.limsehr.domain.Patient limsPatient = patientResolver.resolveAndSavePatient(patient, organisation);
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
