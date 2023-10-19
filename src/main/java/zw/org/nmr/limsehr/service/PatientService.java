package zw.org.nmr.limsehr.service;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.service.dto.PatientDTO;

/**
 * Service class for managing patients.
 */

public interface PatientService {
    Page<Patient> getAllPatients(Pageable pageable);

    Patient savePatient(@Valid Patient patient);

    Patient updatePatient(@Valid PatientDTO patientDTO);

    Page<Patient> searchPatient(Pageable pageable, String text);

    Optional<Patient> getOne(String patientId);
}
