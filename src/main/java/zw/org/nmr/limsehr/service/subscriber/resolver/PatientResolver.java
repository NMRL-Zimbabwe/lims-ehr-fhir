package zw.org.nmr.limsehr.service.subscriber.resolver;

import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.utils.DateUtils;

@Service
public class PatientResolver {

    PatientRepository patientRepository;

    PatientIdentifierResolver patientIdentifierResolver;

    DateUtils dateUtils;

    public PatientResolver(PatientRepository patientRepository, PatientIdentifierResolver patientIdentifierResolver, DateUtils dateUtils) {
        this.patientRepository = patientRepository;
        this.patientIdentifierResolver = patientIdentifierResolver;
        this.dateUtils = dateUtils;
    }

    public zw.org.nmr.limsehr.domain.Patient resolveAndSavePatient(Patient patient) {
        zw.org.nmr.limsehr.domain.Patient pt = new zw.org.nmr.limsehr.domain.Patient();

        pt.setFirstname(patient.getName().get(0).getGiven().get(0).toString());
        pt.setLastname(patient.getName().get(0).getFamily());
        pt.setGender(patient.getGender().toString());
        pt.setDob(dateUtils.convertToLocalDate(patient.getBirthDate()));
        pt.setPatientId(patient.getIdElement().getIdPart());
        pt.setArt(""); // TODO Add Art number which is missing

        // TODO  get Patient telecom from ehr fhir which is currently missing
        // TODO get patient managing organisation for primary referrer

        zw.org.nmr.limsehr.domain.Patient ptSaved = patientRepository.save(pt);
        if (ptSaved != null) {
            patientIdentifierResolver.resolveAndSavePatientIdentifiers(patient.getIdentifier(), pt.getPatientId());
        }

        // TODO Resolve and save patient address

        return ptSaved;
    }
}
