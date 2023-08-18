package zw.org.nmr.limsehr.service.subscriber;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.Task;
import org.hl7.fhir.r4.model.codesystems.TaskStatus;

public class RequestedOrders {

    public void getRequestedOrders() {
        String fhirBaseUrl = "";
        String fhirUsername = "";
        String fhirPassword = "";

        FhirContext ctx = FhirContext.forR4();
        IGenericClient fhirClient = ctx.newRestfulGenericClient(fhirBaseUrl);
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(fhirUsername, fhirPassword);
        fhirClient.registerInterceptor(authInterceptor);

        Bundle taskBundle = fhirClient
            .search()
            .forResource(Task.class)
            .where(Task.STATUS.exactly().code(TaskStatus.REQUESTED.toCode()))
            .returnBundle(Bundle.class)
            .execute();

        for (Bundle.BundleEntryComponent entry : taskBundle.getEntry()) {
            if (entry.getResource() instanceof Task task) {
                // Fetch task with all resources
                Bundle fhirTaskBundle = fhirClient
                    .search()
                    .forResource(Task.class)
                    .where(Task.IDENTIFIER.exactly().code(task.getId()))
                    .include(Task.INCLUDE_ALL)
                    .returnBundle(Bundle.class)
                    .execute();

                // Retrieve the included Patient resource
                if (task.hasFocus() && task.getFocus().getResource() instanceof Patient) {
                    Patient patient = (Patient) task.getFocus().getResource();
                    // Process the patient resource
                }

                // Retrieve the included ServiceRequest resource
                if (task.hasFor() && task.getFor().getResource() instanceof ServiceRequest) {
                    ServiceRequest serviceRequest = (ServiceRequest) task.getFor().getResource();
                    // Process the service request resource
                }

                // When all is done update Task to received
                if (task.getStatus().equals(Task.TaskStatus.REQUESTED)) {
                    task.setStatus(Task.TaskStatus.RECEIVED);
                    fhirClient.update().resource(task).execute();
                }
            }
        }
    }
}
