package zw.org.nmr.limsehr.service.impl;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.PatientAddressService;
import zw.org.nmr.limsehr.service.PatientIdentifierService;
import zw.org.nmr.limsehr.service.PatientPhoneService;
import zw.org.nmr.limsehr.service.PatientService;
import zw.org.nmr.limsehr.service.dto.PatientDTO;
import zw.org.nmr.limsehr.service.utility.DateUtility;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final DateUtility dateUtility;

    private final PatientPhoneService phoneService;

    private final PatientAddressService patientAddressService;

    private final PatientIdentifierService patientIdentifierService;

    public PatientServiceImpl(
        PatientRepository patientRepository,
        DateUtility dateUtility,
        PatientPhoneService phoneService,
        PatientAddressService patientAddressService,
        PatientIdentifierService patientIdentifierService
    ) {
        this.patientRepository = patientRepository;
        this.dateUtility = dateUtility;
        this.phoneService = phoneService;
        this.patientAddressService = patientAddressService;
        this.patientIdentifierService = patientIdentifierService;
    }

    @Override
    public Patient savePatient(@Valid Patient patient) {
        if (patient.getPatientId() == null) {
            patient.setPatientId(UUID.randomUUID().toString());
        }

        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Patient> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public Optional<Patient> getPatientByPatientId(String patientId) {
        return patientRepository.findByPatientId(patientId);
    }

    /*
     * Update patient details
     */
    @Override
    public Patient updatePatient(PatientDTO patientDTO) {
        Optional<Patient> pt = patientRepository.findByPatientId(patientDTO.getPatientId());
        if (pt.isPresent()) {
            return null;
        }
        log.debug("Update patient: {}", patientDTO.getPatientId());

        Patient patient = new Patient();

        patient.setPatientId(patientDTO.getPatientId());
        patient.setFirstname(patientDTO.getFirstname());
        patient.setLastname(patientDTO.getLastname());
        patient.setGender(patientDTO.getGender());

        return patientRepository.save(patient);
    }

    public void updatePatientErrorLog(Patient patient, String reason) {
        patient.setRetry(patient.getRetry() + 1);
        patient.setErrorReason(reason);
        patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> getOne(String patientId) {
        return patientRepository.findByPatientId(patientId);
    }

    @Override
    public Page<Patient> searchPatient(Pageable pageable, String text) {
        return patientRepository.findByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(pageable, text, text);
    }
}
