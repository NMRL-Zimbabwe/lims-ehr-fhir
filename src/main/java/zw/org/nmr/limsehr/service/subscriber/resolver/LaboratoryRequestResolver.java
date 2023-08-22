package zw.org.nmr.limsehr.service.subscriber.resolver;

import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;

@Service
public class LaboratoryRequestResolver {

    LaboratoryRequestRepository laboratoryRequestRepository;

    public LaboratoryRequestResolver(LaboratoryRequestRepository laboratoryRequestRepository) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    public LaboratoryRequest resolveAndSaveLaboratoryRequest(
        ServiceRequest serviceRequest,
        Specimen specimen,
        Patient patient,
        Location laboratory,
        Location facility
    ) {
        LaboratoryRequest labRequest = new LaboratoryRequest();

        labRequest.setMiddlewareAnalysisRequestUuid(serviceRequest.getIdElement().getIdPart());
        labRequest.setLaboratoryRequestId(serviceRequest.getIdElement().getIdPart());
        labRequest.setMiddlewareClientUuid(serviceRequest.getIdElement().getIdPart());

        labRequest.setPatient(patient);

        // TODO this field is empty
        //        labRequest.setDateCollected();
        labRequest.setSampleId("");

        labRequest.setLabId(laboratory.getIdElement().getIdPart());
        labRequest.setLabName(laboratory.getName());
        labRequest.setClientId(facility.getIdElement().getIdPart());
        labRequest.setClient(facility.getName());

        for (Coding testCode : specimen.getType().getCoding()) {
            if (testCode.getSystem().equals("http://loinc.org")) {
                labRequest.setTestId(testCode.getCode());
            }
        }
        //
        for (Identifier identifier : specimen.getIdentifier()) {
            if (identifier.getType().getText().equals("Laboratory Request Number")) {
                labRequest.setClientSampleId(identifier.getValue());
            }
        }

        return laboratoryRequestRepository.save(labRequest);
    }
}
