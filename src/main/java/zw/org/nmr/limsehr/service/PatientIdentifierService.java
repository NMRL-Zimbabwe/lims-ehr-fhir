package zw.org.nmr.limsehr.service;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.PatientIdentifier;
import zw.org.nmr.limsehr.repository.PatientIdentifierRepository;
import zw.org.nmr.limsehr.service.dto.patient.resolver.PatientIdentifierDTO;

/**
 * Service class for managing patient Phone.
 */
@Service
@Transactional
public class PatientIdentifierService {

    private final Logger log = LoggerFactory.getLogger(PatientIdentifierService.class);

    private final PatientIdentifierRepository patientIdentifierRepository;

    public PatientIdentifierService(PatientIdentifierRepository patientIdentifierRepository) {
        this.patientIdentifierRepository = patientIdentifierRepository;
    }

    public PatientIdentifier addPatientIdentifier(PatientIdentifierDTO identifier, String patientId) throws Exception {
        PatientIdentifier id = patientIdentifierRepository.findByNumber(identifier.getNumber());

        if (id != null) {
            log.debug("Patient Identifier Already exist!!!!!!! {}", identifier.getNumber());
            return id;
        }

        if (identifier.getNumber() == null) {
            throw new Exception("No patient identifier number found.....");
        }

        PatientIdentifier pid = new PatientIdentifier();

        pid.setPatientIdentifierId(UUID.randomUUID().toString());
        pid.setNumber(identifier.getNumber());
        if (identifier.getType() != null) {
            pid.setType(identifier.getType().getName());
            pid.setTypeId(identifier.getType().getId());
        }

        pid.setPatientId(patientId);

        PatientIdentifier idSaved = patientIdentifierRepository.save(pid);
        log.debug("Identifier Saved............ {}", idSaved);
        return idSaved;
    }
}
