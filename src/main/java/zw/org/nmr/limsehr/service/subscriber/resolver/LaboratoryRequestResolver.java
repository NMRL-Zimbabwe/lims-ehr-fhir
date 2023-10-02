package zw.org.nmr.limsehr.service.subscriber.resolver;

import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;

@Service
public class LaboratoryRequestResolver {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestResolver.class);

    LaboratoryRequestRepository laboratoryRequestRepository;

    public LaboratoryRequestResolver(LaboratoryRequestRepository laboratoryRequestRepository) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    public LaboratoryRequest resolveAndSaveLaboratoryRequest(
        Task task,
        ServiceRequest serviceRequest,
        Specimen specimen,
        Patient patient,
        Location laboratory,
        Location facility
    ) {
        LaboratoryRequest labRequest = new LaboratoryRequest();

        // Task
        labRequest.setMiddlewareAnalysisRequestUuid(task.getIdElement().getIdPart());

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

        log.debug("Specimen {}", specimen.getType().getCoding());

        for (Coding testCode : specimen.getType().getCoding()) {
            log.debug("testCode {}", testCode);
            //   "http://loinc.org"
            if (testCode.getSystem().equals("urn:lims:code")) {
                labRequest.setTestId(testCode.getCode());
            } else {
                labRequest.setTestId("Lindsay");
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
