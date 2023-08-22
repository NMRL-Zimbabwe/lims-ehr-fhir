package zw.org.nmr.limsehr.service.subscriber.reference;

import java.util.Collections;
import org.hl7.fhir.r4.model.Task;

//Order receipt confirmer
public class OrderReceiveConfirmer {

    private OrderReceiveConfirmer() {}

    /**Note:: you need to confirm the receipt of this electronic Task(Order)
     * as soon as you save it in your local (lims) database**/
    public static void confirmTaskReceived(Task task) {
        task.setStatus(Task.TaskStatus.RECEIVED);

        //Save this Task/Order in the Shared Health Record (SHR):: OpenHIE
        FhirResourcesSaver.saveFhirResources(Collections.singletonList(task));
    }
}
