package zw.org.nmr.limsehr.service;

import java.time.LocalDate;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.repository.ClientRepository;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.repository.PatientAddressRepository;
import zw.org.nmr.limsehr.repository.PatientIdentifierRepository;
import zw.org.nmr.limsehr.repository.PatientPhoneRepository;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.dto.AcknowledgementFromLims;
import zw.org.nmr.limsehr.service.dto.RegistrationFromLims;
import zw.org.nmr.limsehr.service.dto.laboratory.request.Coding;
import zw.org.nmr.limsehr.service.dto.laboratory.request.LaboratoryRequestEhrDTO;
import zw.org.nmr.limsehr.service.enums.LaboratoryRequestStatus;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class LaboratoryRequestService {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestService.class);

    private final LaboratoryRequestRepository laboratoryRequestRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientPhoneRepository phoneRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PatientIdentifierRepository patientIdentifierRepository;

    @Autowired
    PatientAddressRepository patientAddressRepository;

    public LaboratoryRequestService(LaboratoryRequestRepository laboratoryRequestRepository) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    public LaboratoryRequest saveLaboratoryRequest(LaboratoryRequestEhrDTO laboratoryRequestDTO, String patientId) {
        //log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", laboratoryRequestDTO);

        log.info("laboratory Request DTO Get Id :{} ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", laboratoryRequestDTO);

        LaboratoryRequest labreq = new LaboratoryRequest();
        LaboratoryRequest checkExist = laboratoryRequestRepository.findByMiddlewareAnalysisRequestUuid(laboratoryRequestDTO.getId());

        if (checkExist != null) {
            return checkExist;
        }

        Patient patient = patientRepository.findOneByPatientId(patientId);

        /**
         * EHR central repository static terminology coding standards
         * LABORATORY_CONTEXT_ID, FACILITY_CONTEXT_ID
         */
        final String LABORATORY_CONTEXT_ID = "b8a29ede-6ccc-4229-9b2a-c280baaaa2c7";
        final String FACILITY_AGENT_ID = "329f1cbb-72ae-4ef9-80d9-9dbf77f53316";

        if (patient != null) {
            LaboratoryRequest labRequest = new LaboratoryRequest();

            // Resolve facility codes
            labRequest.setLaboratoryRequestId(UUID.randomUUID().toString()); // Id for the system

            Coding[] facilityCodes = laboratoryRequestDTO.getFacility().getCoding();
            String facilityCode = null;
            for (Coding code : facilityCodes) {
                // loop though for HIS coding standards
                if (code.getAgentContext().getAgent().getId().equals(FACILITY_AGENT_ID)) {
                    facilityCode = code.getCode();
                }
            }

            String laboratoryCode = null;

            Coding[] laboratoryCodes = laboratoryRequestDTO.getLaboratory().getCoding();
            for (Coding code : laboratoryCodes) {
                // loop though for Laboratory coding standards
                if (code.getAgentContext().getContext().getId().equals(LABORATORY_CONTEXT_ID)) {
                    log.info("Laboratory context :{}", code.getAgentContext().getContext());
                    laboratoryCode = code.getCode();
                }
            }

            if (facilityCode == null) {
                facilityCode = "UNKNOWN CLient";
            }

            if (laboratoryCode == null) {
                laboratoryCode = "UNKNOWN Laboratory";
            }

            labRequest.setSampleId(laboratoryRequestDTO.getSample().getName());
            labRequest.setTestId(laboratoryRequestDTO.getTest().getName());
            labRequest.setClientId(facilityCode);
            labRequest.setClient(facilityCode);
            labRequest.setMiddlewareClientUuid(laboratoryRequestDTO.getFacility().getId());
            labRequest.setMiddlewareAnalysisRequestUuid(laboratoryRequestDTO.getId()); // uuid from middleware
            labRequest.setClientSampleId(laboratoryRequestDTO.getClientReference()); // client sample ID
            labRequest.setLabId(laboratoryCode);
            labRequest.setLabName(laboratoryCode);

            labRequest.setDateCollected(laboratoryRequestDTO.getDate());
            labRequest.setPatient(patient);

            labreq = laboratoryRequestRepository.save(labRequest);
        }

        return labreq;
    }

    public void flushOurErrorsFromQueue(LaboratoryRequest request, String error_reason) {
        request.setRetry(request.getRetry() + 1);
        request.setErrorReason(error_reason);
        laboratoryRequestRepository.save(request);
    }

    public LaboratoryRequest updateLaoratoryRequest(AcknowledgementFromLims obj) {
        LaboratoryRequest fromLims = laboratoryRequestRepository.findByClientSampleId(obj.getClientSampleId());

        if (fromLims != null) {
            fromLims.setAcknowledgeSampleReceipt(LaboratoryRequestStatus.ACKNOWLEDGE_RECEIPT.toString());
            fromLims.setLabReferenceSampleId(obj.getSampleId());

            if (obj.getStatus() != null) {
                fromLims.setStatus(obj.getStatus());
            }

            if (obj.getResult() != null) {
                fromLims.setResult(obj.getResult());
                fromLims.setDateResultReceivedFromLims(LocalDate.now());
            }

            if (obj.getUnit() != null) {
                fromLims.setUnit(obj.getUnit());
            }

            if (obj.getDateTested() != null) {
                fromLims.setDateTested(obj.getDateTested());
            }

            laboratoryRequestRepository.save(fromLims);
            return fromLims;
        } else {
            log.debug("Request does not exist: {}", fromLims);
            return fromLims;
        }
    }
}
