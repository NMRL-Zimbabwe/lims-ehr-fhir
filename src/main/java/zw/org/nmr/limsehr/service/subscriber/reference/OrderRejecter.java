package zw.org.nmr.limsehr.service.subscriber.reference;

import java.util.Collections;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Task;

public class OrderRejecter {

    private OrderRejecter() {}

    public static void rejectOrder(Task task) {
        task.setStatus(Task.TaskStatus.REJECTED);

        CodeableConcept rejectionReason = new CodeableConcept();

        Coding limsRejectReasonCode = new Coding();
        limsRejectReasonCode.setSystem("http://mohcc.lims.org"); //Let's agree on this system pliz
        limsRejectReasonCode.setCode("RR001");
        limsRejectReasonCode.setDisplay("Specimen lacked proper identification");

        rejectionReason.addCoding(limsRejectReasonCode);
        /*Here you may also include codes in other systems for e.g Loinc which is recommended*/
        //rejectionReason.addCoding(loincRejectReasonCode);
        //rejectionReason.addCoding(snomedRejectReasonCode);
        //rejectionReason.addCoding(impiloRejectReasonCode); //Not that recommended

        task.setStatusReason(rejectionReason);

        //Save this Task/Order in the Shared Health Record (SHR):: OpenHIE
        FhirResourcesSaver.saveFhirResources(Collections.singletonList(task));
    }
}
