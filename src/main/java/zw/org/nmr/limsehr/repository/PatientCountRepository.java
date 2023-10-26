package zw.org.nmr.limsehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.org.nmr.limsehr.domain.Patient;

/**
 * Spring Data JPA repository for the {@link Patient} entity.
 */

public interface PatientCountRepository extends JpaRepository<Patient, Long> {
    // Additional custom query methods can be defined here if needed
}
