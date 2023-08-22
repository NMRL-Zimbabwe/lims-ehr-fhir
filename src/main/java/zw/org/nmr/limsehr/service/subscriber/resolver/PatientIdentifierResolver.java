package zw.org.nmr.limsehr.service.subscriber.resolver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hl7.fhir.r4.model.Identifier;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.PatientIdentifier;
import zw.org.nmr.limsehr.repository.PatientIdentifierRepository;

@Service
public class PatientIdentifierResolver {

    PatientIdentifierRepository patientIdentifierRepository;

    public PatientIdentifierResolver(PatientIdentifierRepository patientIdentifierRepository) {
        this.patientIdentifierRepository = patientIdentifierRepository;
    }

    public void resolveAndSavePatientIdentifiers(List<Identifier> identifiers, String patientId) {
        for (Identifier identifier : identifiers) {
            Optional<PatientIdentifier> identity = this.find(identifier.getType().getText(), patientId);
            PatientIdentifier patientIdentifier = identity.orElse(null);
            if (patientIdentifier != null) {
                patientIdentifier.setNumber(identifier.getValue());
                this.save(patientIdentifier);
            } else {
                PatientIdentifier newPID = new PatientIdentifier();
                newPID.setPatientIdentifierId(UUID.randomUUID().toString());
                newPID.setNumber(identifier.getValue());
                newPID.setType(identifier.getType().getText());
                newPID.setTypeId(identifier.getSystem());
                newPID.setPatientId(patientId);
                this.save(newPID);
            }
        }
    }

    public Optional<PatientIdentifier> find(String identifierType, String patientId) {
        return patientIdentifierRepository.findByPatientIdAndType(patientId, identifierType);
    }

    public PatientIdentifier save(PatientIdentifier identifier) {
        return patientIdentifierRepository.save(identifier);
    }
}
