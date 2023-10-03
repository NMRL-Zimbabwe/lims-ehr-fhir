package zw.org.nmr.limsehr.service.subscriber.resolver;

import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Organization;
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

    public zw.org.nmr.limsehr.domain.Patient resolveAndSavePatient(Patient patient, Organization organisation) {
        zw.org.nmr.limsehr.domain.Patient pt = new zw.org.nmr.limsehr.domain.Patient();

        pt.setFirstname(patient.getName().get(0).getGiven().get(0).toString());
        pt.setLastname(patient.getName().get(0).getFamily());
        pt.setGender(patient.getGender().toString());
        pt.setDob(dateUtils.convertToLocalDate(patient.getBirthDate()));
        pt.setPatientId(patient.getIdElement().getIdPart());

        if (patient.getIdentifier() != null) {
            for (Identifier identifier : patient.getIdentifier()) {
                if (identifier.getSystem().equalsIgnoreCase("urn:impilo:art")) {
                    pt.setArt(identifier.getValue());
                }
            }
        }
        // TODO  get Patient telecom from ehr fhir which is currently missing
        // if(!patient.getTelecom().isEmpty()) {
        // pt.setPhone(patient.getTelecom().get(0).getValue())
        // }
        for (Extension ext : patient.getExtension()) {
            //        	if(ext.getUrl().equalsIgnoreCase("urn:consent:sms")) {
            //        		pt.setConsentSms((BooleanType) ext.getValue());
            //        	}
            //         	if(ext.getUrl().equalsIgnoreCase("urn:impilo:cohort")) {
            //         		StringType val = (StringType) ext.getValue("value");
            //        		patientIdentifierResolver.addPatientIdentifier(
            //        				pt.getPatientId(),
            //        				"Cohort Number",
            //        				ext.getUrl(),
            //        				val.getValue()
            //        		);
            //        	}
        }

        // TODO get patient managing organisation for primary referrer
        pt.setPrimary_referrer(organisation.getName());
        for (Identifier poId : organisation.getIdentifier()) {
            if (poId.getSystem().equalsIgnoreCase("urn:impilo:uid")) {
                pt.setPrimary_referrer_id(poId.getValue());
            }
        }

        zw.org.nmr.limsehr.domain.Patient ptSaved = patientRepository.save(pt);
        if (ptSaved != null) {
            patientIdentifierResolver.resolveAndSavePatientIdentifiers(patient.getIdentifier(), pt.getPatientId());
        }

        // TODO Resolve and save patient address

        return ptSaved;
    }
}
