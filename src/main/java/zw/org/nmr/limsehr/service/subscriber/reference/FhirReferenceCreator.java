package zw.org.nmr.limsehr.service.subscriber.reference;

import org.hl7.fhir.r4.model.Reference;

public class FhirReferenceCreator {

    private FhirReferenceCreator() {}

    public static Reference getReference(String id, String type) {
        Reference reference = new Reference();
        reference.setReference(type + "/" + id);
        reference.setType(type);
        return reference;
    }
}
