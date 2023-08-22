package zw.org.nmr.limsehr.repository;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.PatientIdentifier;

/**
 * Spring Data JPA repository for the {@link PatientIdentifier} entity.
 */
@Repository
public interface PatientIdentifierRepository extends JpaRepository<PatientIdentifier, String> {
    PatientIdentifier findByNumber(String number);

    Collection<PatientIdentifier> findByPatientId(String patientId);

    Optional<PatientIdentifier> findByPatientIdAndType(String patientId, String identifierType);
}
