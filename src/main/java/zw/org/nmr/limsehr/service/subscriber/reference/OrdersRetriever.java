package zw.org.nmr.limsehr.service.subscriber.reference;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.util.ArrayList;
import java.util.List;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Task;

public class OrdersRetriever {

    private OrdersRetriever() {}

    //Get Lab Request Orders from Impilo Facilities or Sites in Fhir format
    public static List<Task> getRequestedTasks() {
        List<Task> tasks = new ArrayList<>();

        IGenericClient client = FhirClientUtility.getFhirClient();

        Bundle bundle = client
            .search()
            .forResource(Task.class)
            .where(Task.STATUS.exactly().code(Task.TaskStatus.REQUESTED.toCode()))
            .returnBundle(Bundle.class)
            .execute();

        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.hasResource()) {
                switch (entry.getResource().getResourceType()) {
                    case Task:
                        Task task = (Task) entry.getResource();
                        tasks.add(task);
                        break;
                    default:
                        break;
                }
            }
        }

        return tasks;
    }
}
