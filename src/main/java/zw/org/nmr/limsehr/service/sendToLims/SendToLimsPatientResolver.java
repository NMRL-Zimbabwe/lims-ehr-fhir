package zw.org.nmr.limsehr.service.sendToLims;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.domain.PatientAddress;
import zw.org.nmr.limsehr.domain.PatientIdentifier;
import zw.org.nmr.limsehr.domain.PatientPhone;
import zw.org.nmr.limsehr.repository.PatientAddressRepository;
import zw.org.nmr.limsehr.repository.PatientIdentifierRepository;
import zw.org.nmr.limsehr.repository.PatientPhoneRepository;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.LaboratoryRequestService;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestCountryState;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestPatientDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestPatientIdentifiersDTO;

@Service
public class SendToLimsPatientResolver {

    // Culani

    private static final Logger log = LoggerFactory.getLogger(SendToLimsPatientResolver.class);

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientPhoneRepository phoneRepository;

    @Autowired
    PatientIdentifierRepository patientIdentifierRepository;

    @Autowired
    PatientAddressRepository patientAddressRepository;

    @Autowired
    LaboratoryRequestService laboratoryRequestService;

    public UnifiedLimsRequestPatientDTO resolvePatient(Patient patient, LaboratoryRequest request) {
        UnifiedLimsRequestPatientDTO pt = new UnifiedLimsRequestPatientDTO();

        pt.setFirstname(patient.getFirstname());
        pt.setSurname(patient.getLastname());
        // pt.setClientID(request.getClient());ZW030990

        pt.setClientID(request.getClientId());

        if (patient.getArt() != null) {
            pt.setClientPatientID(patient.getArt().replace("-", ""));
        } else {
            log.error("Client Patient ID not found");
            laboratoryRequestService.flushOurErrorsFromQueue(request, "Client Patient ID not found");
            return null;
        }

        pt.setGender(patient.getGender());
        pt.setBirthDateEstimated(false);
        pt.setBirthDate(patient.getDob().toString());

        PatientPhone phone = phoneRepository.findFirstByPatientIdOrderByLastModifiedDateDesc(request.getPatient().getPatientId());

        /**
         * If patient record has a corresponding phone number, add it to the record.
         * Assumption -> all patients with phone numbers have consented to SMS
         */

        if (phone != null) {
            pt.setMobilePhone(phone.getNumber());
            pt.setConsentSMS(true);
        } else {
            pt.setMobilePhone("");
        }

        /**
         * If the patient has got corresponding identifiers, add them to the patient
         * record.
         */

        Collection<PatientIdentifier> patientIdentifiers = patientIdentifierRepository.findByPatientId(patient.getPatientId());

        UnifiedLimsRequestPatientIdentifiersDTO identification[] = new UnifiedLimsRequestPatientIdentifiersDTO[patientIdentifiers.size()];

        int index = 0;
        for (PatientIdentifier identifier : patientIdentifiers) {
            if (identifier.getNumber() != null) {
                String type = "National ID";

                if (identifier.getType() != null) {
                    type = identifier.getType();
                }
                // log.debug("test identifier {}", identifier);

                identification[index++] = new UnifiedLimsRequestPatientIdentifiersDTO(identifier.getNumber(), type);
            }
        }

        pt.setPatientIdentifiers(identification);

        /**
         * If patient has an address add it to the patient record
         */

        PatientAddress patientAddress = patientAddressRepository.findByPatientId(patient.getPatientId());

        if (patientAddress != null) {
            UnifiedLimsRequestCountryState address = new UnifiedLimsRequestCountryState();

            address.setCountry("Zimbabwe");
            address.setState("Harare");
            address.setDistrict("Harare");
            pt.setCountryState(address);
        }
        return pt;
    }
}
