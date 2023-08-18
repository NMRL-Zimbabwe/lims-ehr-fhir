package zw.org.nmr.limsehr.service;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.PatientAddress;
import zw.org.nmr.limsehr.repository.PatientAddressRepository;
import zw.org.nmr.limsehr.service.dto.patient.resolver.PatientAddressResolverDTO;

@Service
public class PatientAddressService {

    private final Logger log = LoggerFactory.getLogger(PatientPhoneService.class);

    private final PatientAddressRepository patientAddressRepository;

    public PatientAddressService(PatientAddressRepository patientAddressRepository) {
        this.patientAddressRepository = patientAddressRepository;
    }

    public PatientAddress addPatientAddress(PatientAddressResolverDTO patientaddress, String patientId) {
        PatientAddress add = patientAddressRepository.findByPatientId(patientId);

        if (add != null) {
            log.debug("Patient Address Already exist!!!!!!! {}", patientaddress.getStreet());
            return add;
        } else {
            PatientAddress address = new PatientAddress();

            address.setPatientAddressId(UUID.randomUUID().toString());
            address.setCity(patientaddress.getCity());
            address.setStreet(patientaddress.getStreet());

            if (patientaddress.getTown() != null) {
                address.setTownId(patientaddress.getTown().getId());
                address.setTownName(patientaddress.getTown().getName());
            } else {
                address.setTownId("");
                address.setTownName("");
            }

            address.setPatientId(patientId);

            PatientAddress addSaved = patientAddressRepository.save(address);
            log.debug("Address Saved............ {}", addSaved);
            return addSaved;
        }
    }
}
