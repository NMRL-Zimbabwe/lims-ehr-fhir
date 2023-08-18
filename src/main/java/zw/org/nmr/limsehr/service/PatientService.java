package zw.org.nmr.limsehr.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.dto.patient.art.resolver.PersonArt;
import zw.org.nmr.limsehr.service.dto.patient.resolver.PatientArtResolverDTO;
import zw.org.nmr.limsehr.service.dto.patient.resolver.PatientIdentifierDTO;
import zw.org.nmr.limsehr.service.dto.patient.resolver.PatientResolverDTO;
import zw.org.nmr.limsehr.service.utility.DateUtility;

/**
 * Service class for managing patients.
 */
@Service
@Transactional
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final DateUtility dateUtility;

    private final PatientPhoneService phoneService;

    private final PatientAddressService patientAddressService;

    private final PatientIdentifierService patientIdentifierService;

    public PatientService(
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

    /*
     * Create a new patient
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Patient createPatient(PatientResolverDTO patientResolver, String patientId) throws Exception {
        Patient request = patientRepository.findByPatientId(patientId);

        Patient patient = new Patient();

        if (request != null) {
            log.debug("Patient already exit: {}", request);
            return request;
        } else {
            patient.setPatientId(patientId);
            patient.setFirstname(patientResolver.getFirstname());
            patient.setLastname(patientResolver.getLastname());

            if (patientResolver.getBirthdate() != null) {
                patient.setDob(dateUtility.stringToLocalDate(patientResolver.getBirthdate()));
            }
            patient.setGender(patientResolver.getSex());

            if (patientResolver.getArt() != null) {
                for (PatientArtResolverDTO artNumber : patientResolver.getArt()) {
                    if (artNumber != null) artNumber.getNumber();
                    patient.setArt(artNumber.getNumber());
                }
            } else {
                //patient.setArt("PENDING_RESOLVE");
            }

            Patient savedPatient = patientRepository.save(patient);

            log.debug("Saved Patient: {}", savedPatient);

            /**
             * If the payload containts patient phone(s). Add the phone number(s) to the
             * database
             */
            if (patientResolver.getPhone() != null && savedPatient != null) {
                for (String phone : patientResolver.getPhone()) {
                    phoneService.addPatientPhone(phone, patientId);
                }
            }

            /**
             * If the payload containts patient address. Add the patient address to the
             * database
             */

            if (patientResolver.getAddress() != null && patientResolver.getAddress().getCity() != null && savedPatient != null) {
                patientAddressService.addPatientAddress(patientResolver.getAddress(), patientId);
            }

            /**
             * If the payload containts patient identifier(s). Add the patient identifier to
             * the database
             */

            if (patientResolver.getIdentification() != null && savedPatient != null) {
                for (PatientIdentifierDTO identifier : patientResolver.getIdentification()) {
                    patientIdentifierService.addPatientIdentifier(identifier, patientId);
                }
            }

            // TODO ADD Address
            // TODO Add Identification

            return savedPatient;
        }
    }

    /*
     * Update patient details
     */
    public Patient updatePatient(PatientResolverDTO patientResolver, String patientId) throws Exception {
        Patient request = patientRepository.findByPatientId(patientId);

        Patient patient = new Patient();

        if (request != null) {
            patient.setPatientId(patientId);
            patient.setFirstname(patientResolver.getFirstname());
            patient.setLastname(patientResolver.getLastname());
            patient.setGender(patientResolver.getSex());

            if (patientResolver.getBirthdate() != null) {
                patient.setDob(dateUtility.stringToLocalDate(patientResolver.getBirthdate()));
            }

            if (patientResolver.getArt() != null) {
                for (PatientArtResolverDTO artNumber : patientResolver.getArt()) {
                    if (artNumber != null) artNumber.getNumber();
                    patient.setArt(artNumber.getNumber());
                }
            }

            log.debug("Update patient: {}", patient.getPatientId());

            Patient updatedPatient = patientRepository.save(patient);

            /**
             * If the payload containts patient phone(s). Add the phone number(s) to the
             * patient Phone database table
             **/
            if (patientResolver.getPhone() != null && updatedPatient != null) {
                for (String phone : patientResolver.getPhone()) {
                    phoneService.addPatientPhone(phone, patientId);
                }
            }

            return patient;
        } else {
            throw new Exception("Could not update patient..........");
        }
    }

    /*
     * Update patient details
     */
    public Patient updatePatientArt(Patient patient, List<PersonArt> arts) throws Exception {
        log.info("Update patient ART details!!!!!!!!!!!! :{}", patient.getPatientId());
        if (arts != null) {
            String artNumber = "";
            for (PersonArt number : arts) {
                if (number != null) {
                    log.info("Update patient ART Details: {}", number);
                    artNumber = number.getNumber();

                    if (number.getNumber() == null) {
                        updatePatientErrorLog(patient, "ART  number is null");
                        log.info("Art number is null or empty");
                    }
                } else {
                    updatePatientErrorLog(patient, "ART number is null");
                }
            }

            //log.info("Update patient ART Details: {}", artNumber);

            patient.setArt(artNumber);
            patient.setRetry(patient.getRetry() + 1);

            if (artNumber.equals("")) {
                updatePatientErrorLog(patient, "Normal Art Update Failed");
                throw new Exception("Could not update patient ART..........");
            }
            log.info("Update patient ENTITY: {}", patient);
            return patientRepository.save(patient);
        } else {
            updatePatientErrorLog(patient, "Failed updating ART");
            throw new Exception("Could not update patient ART..........");
        }
    }

    public void updatePatientErrorLog(Patient patient, String reason) {
        patient.setRetry(patient.getRetry() + 1);
        patient.setErrorReason(reason);
        patientRepository.save(patient);
    }
}
