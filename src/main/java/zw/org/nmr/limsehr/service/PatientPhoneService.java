package zw.org.nmr.limsehr.service;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.PatientPhone;
import zw.org.nmr.limsehr.repository.PatientPhoneRepository;

/**
 * Service class for managing patient Phone.
 */
@Service
@Transactional
public class PatientPhoneService {

    private final Logger log = LoggerFactory.getLogger(PatientPhoneService.class);

    private final PatientPhoneRepository patientPhoneRepository;

    public PatientPhoneService(PatientPhoneRepository phoneRepository) {
        this.patientPhoneRepository = phoneRepository;
    }

    public PatientPhone addPatientPhone(String phone, String patientId) {
        PatientPhone ph = patientPhoneRepository.findByNumber(phone);

        if (ph != null) {
            log.debug("Patient phone Already exist!!!!!!! {}", phone);
            return ph;
        } else {
            PatientPhone patientPhone = new PatientPhone();

            patientPhone.setPatientPhoneId(UUID.randomUUID().toString());
            patientPhone.setNumber(phone);
            patientPhone.setPatientId(patientId);

            PatientPhone phoneSaved = patientPhoneRepository.save(patientPhone);
            log.debug("Phone Saved............ {}", phoneSaved);
            return phoneSaved;
        }
    }
}
