package zw.org.nmr.limsehr.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Patient;

/**
 * Spring Data JPA repository for the {@link Patient} entity.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    Patient findOneByPatientId(String patientId);

    Optional<Patient> findByPatientId(String patientId);

    List<Patient> findFirst25ByArtIsNullAndRetryLessThan(int retry);

    Optional<Patient> findByPatientIdAndArtNot(String patientId, String resolveStatus);

    List<Patient> findFirst25ByArtInAndRetryLessThan(ArrayList<String> artStatuses, int retry);

    Optional<Patient> findByPatientIdAndArtIsNotNullAndRetryLessThan(String patientId, int retry);

    Optional<Patient> findByPatientIdAndRetryLessThan(String patientId, int retry);

    Page<Patient> findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(Pageable pageable, String text, String text2);
}
