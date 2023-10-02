package zw.org.nmr.limsehr.service.sendToLims;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.Client;
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.domain.PatientAddress;
import zw.org.nmr.limsehr.domain.PatientIdentifier;
import zw.org.nmr.limsehr.domain.PatientPhone;
import zw.org.nmr.limsehr.repository.ClientRepository;
import zw.org.nmr.limsehr.repository.LaboratoryRepository;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.repository.PatientAddressRepository;
import zw.org.nmr.limsehr.repository.PatientIdentifierRepository;
import zw.org.nmr.limsehr.repository.PatientPhoneRepository;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.dto.laboratory.request.DTOforLIMS;
import zw.org.nmr.limsehr.service.dto.laboratory.request.SampleDTOforLIMS;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequest;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestAnalysisRequestDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestBatchDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestCountryState;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestPatientDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestPatientIdentifiersDTO;
import zw.org.nmr.limsehr.service.enums.LaboratoryRequestStatus;

@Service
public class SendToLimsService {

    private static final Logger log = LoggerFactory.getLogger(SendToLimsService.class);

    @Autowired
    LaboratoryRequestRepository laboratoryRequestRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientPhoneRepository phoneRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PatientIdentifierRepository patientIdentifierRepository;

    @Autowired
    PatientAddressRepository patientAddressRepository;

    @Autowired
    LaboratoryRepository laboratoryRepository;

    @Autowired
    SendToLimsSampleResolver sendToLimsSampleResolver;

    // @Autowired
    // @Qualifier(value = "senaiteContainerFactory")
    // private AmqpTemplate amqpTemplate;

    public void sendMessageToLims(UnifiedLimsRequest message, String destination) throws JsonProcessingException {
        System.out.println("[******] Waiting for messages.");

        ObjectMapper mapper = new ObjectMapper();
        Object jsonMessage = mapper.writeValueAsString(message);
        // amqpTemplate.convertAndSend("ehr.lims", destination, jsonMessage.toString());
        // amqpTemplate.convertAndSend(jsonMessage);

    }

    @Scheduled(fixedRate = 5000)
    public void requestBuilder() throws Exception {
        /**
         * Search for records that have not been sent to LIMS
         */

        UnifiedLimsRequest unifiedLimsRequest = new UnifiedLimsRequest();
        int retry = 4;
        List<LaboratoryRequest> sendToLims = laboratoryRequestRepository.findBySentToLimsIsNullAndRetryLessThan(retry);
        for (LaboratoryRequest request : sendToLims) {
            Optional<Patient> isPatient = patientRepository.findByPatientIdAndRetryLessThan(request.getPatient().getPatientId(), retry);

            /**
             * Ignore records with empty PENDING_RESOLVE ART
             */

            Patient patient = isPatient.orElseThrow(() -> new Exception("Patient does not exist"));
            /**
             * Construct patient details
             */
            Optional<Laboratory> optLab = laboratoryRepository.findByCode(request.getLabId());
            String destination = null;
            if (optLab.isPresent()) {
                Laboratory laboratory = optLab.orElseThrow(null);
                unifiedLimsRequest.setLabId(laboratory.getId());
                unifiedLimsRequest.setLabName(laboratory.getName());
                destination = laboratory.getType();
            } else {
                if (request.getLabId() != null) {
                    log.error("UNKNOWN Laboratory :{}", request.getLabId());
                }
                flushOurErrorsFromQueue(request, "UNKNOWN Laboratory");
                return;
            }

            UnifiedLimsRequestPatientDTO pt = new UnifiedLimsRequestPatientDTO();

            pt.setFirstname(patient.getFirstname());
            pt.setSurname(patient.getLastname());
            // pt.setClientID(request.getClient());ZW030990

            pt.setClientID(request.getClientId());

            if (patient.getArt() != null && patient.getArt().length() > 4) {
                pt.setClientPatientID(patient.getArt().replace("-", ""));
            } else {
                log.error("Client Patient ID not found");
                flushOurErrorsFromQueue(request, "Client Patient ID not found");
                return;
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

            UnifiedLimsRequestPatientIdentifiersDTO identification[] =
                new UnifiedLimsRequestPatientIdentifiersDTO[patientIdentifiers.size()];

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

            unifiedLimsRequest.setPatient(pt);

            /**
             * Construct case/batch details
             */

            UnifiedLimsRequestBatchDTO analysisCase = new UnifiedLimsRequestBatchDTO();
            analysisCase.setCaseType("VL");
            analysisCase.setClientCaseID("");
            analysisCase.setReasonForVLtest("Routine Viral Load");
            analysisCase.setVLBreastFeeding(false);
            analysisCase.setVLPregnant(false);

            unifiedLimsRequest.setBatch(analysisCase);

            /**
             * Construct laboratory request details
             */

            //            UnifiedLimsRequestAnalysisRequestDTO analysisRequest  = sendToLimsSampleResolver.resolveSample(
            //
            //            );

            UnifiedLimsRequestAnalysisRequestDTO analysisRequest = new UnifiedLimsRequestAnalysisRequestDTO();

            analysisRequest.setDateSampled(request.getDateCollected().toString());

            String profiles[] = new String[] { "Viral Load Plasma" };

            analysisRequest.setProfiles(profiles);

            String sampleType = null;
            log.info("Routing key :{} ", destination);
            log.info("Sample Type :{} ", request.getSampleId());
            switch (destination) {
                /**
                 * Sample types are hard coded at the moment because, different laboratories use
                 * different default sample types. For example BRIDH register samples as Blood
                 * plasma whilst Mpilo registers as whole blood.
                 *
                 */

                case "bridh":
                    if (request.getSampleId().equals("DBS")) {
                        sampleType = "Dried Blood Spot";
                    } else {
                        sampleType = "Blood plasma";
                    }
                    break;
                case "mpilo":
                    if (request.getSampleId().equals("DBS")) {
                        sampleType = "DBS";
                    } else {
                        sampleType = "Whole blood";
                    }
                    break;
                case "marondera":
                    if (request.getSampleId().equals("DBS")) {
                        sampleType = "DBS";
                    } else {
                        sampleType = "Blood plasma";
                    }
                    break;
                case "kadoma":
                    if (request.getSampleId().equals("DBS")) {
                        sampleType = "Dried Blood Spot";
                    } else {
                        sampleType = "Blood plasma";
                    }
                    break;
                case "chinhoyi":
                    if (request.getSampleId().equals("DBS")) {
                        sampleType = "Dried Blood Spot";
                    } else {
                        sampleType = "Blood plasma";
                    }
                    break;
                case "nmrl":
                    if (request.getSampleId().equals("DBS")) {
                        sampleType = "Dried Blood Spot";
                    } else {
                        sampleType = "Blood plasma";
                    }
                    break;
                case "epworth":
                    if (request.getSampleId().equals("DBS")) {
                        sampleType = "Dried Blood Spot";
                    } else {
                        sampleType = "Blood plasma";
                    }
                    break;
            }

            if (sampleType == null) {
                log.error("No sample type was found");
                flushOurErrorsFromQueue(request, "No sample type was found");
                return;
            }
            analysisRequest.setSampleType(sampleType);
            analysisRequest.setContact("Sister in charge");

            if (request.getClientSampleId() != null) {
                analysisRequest.setClientSampleId(request.getClientSampleId());
            }

            analysisRequest.setTemplate("");
            unifiedLimsRequest.setAnalysisRequest(analysisRequest);

            // The code below will be substituted with the bundled request

            DTOforLIMS builder = new DTOforLIMS();

            builder.setFirstname(patient.getFirstname());
            builder.setSurname(patient.getLastname());
            builder.setGender(patient.getGender());
            builder.setBirthDate(patient.getDob().toString());

            String clientStatus = "active";

            Client client = clientRepository.findByClientIdAndParentPath(request.getClientId(), clientStatus);

            if (client == null) {
                //builder.setPrimaryReferrer(client.getId());
                log.error("Client ID not found or not active :{} ", request.getClientId());
                flushOurErrorsFromQueue(request, "Client ID not found or not active");
                return;
            } else {
                builder.setPrimaryReferrer(client.getId());
            }

            // log.debug("Client information {}", client);

            builder.setParent_path(client.getPath());
            builder.setPortal_type("Patient");
            if (patient.getArt() != null) {
                builder.setClientPatientId(patient.getArt().replace("-", ""));
            }

            SampleDTOforLIMS sample = new SampleDTOforLIMS();
            sample.setDateSampled(request.getDateCollected().toString());
            sample.setPortal_type("AnalysisRequest");
            sample.setParent_path(client.getPath());

            sample.setClientSampleId(request.getClientSampleId());

            // TODO Map actual values corresponding to EHR Integration
            sample.setPatient(request.getPatient().getPatientId());
            sample.setProfiles("65a688c6a45e4bc39511c0a824bf3572");
            sample.setSampleType("306d0f4aefa9452d9e2e936915b1e5ca");

            builder.setSample(sample);

            if (unifiedLimsRequest.getPatient().getClientPatientID() != null && destination != null) {
                sendMessageToLims(unifiedLimsRequest, destination); // Flag sent request as sent to LIMS
                request.setSentToLims(LaboratoryRequestStatus.SENT_TO_LIMS.toString());

                // TODO move the persistence process to a service
                laboratoryRequestRepository.save(request);

                log.info("Sending UNIFIED messege to LIMS for ******* :{} ", unifiedLimsRequest.getPatient().getSurname());
            } else {
                /**
                 * if analysis request does not contain Client patientID reject for now. LIMS is
                 * currently using OIART numbers as identifier so null values will be ignored
                 *
                 */
                log.info("ClientPatientID  is null :{} ", request);
                request.setStatus(LaboratoryRequestStatus.REJECTED.toString());
                request.setSentToLims(LaboratoryRequestStatus.DECLINED.toString());
                request.setErrorReason("Rejected No Client Patient ID found");
                laboratoryRequestRepository.save(request);
            }
        }
    }

    private void flushOurErrorsFromQueue(LaboratoryRequest request, String error_reason) {
        request.setRetry(request.getRetry() + 1);
        request.setErrorReason(error_reason);
        laboratoryRequestRepository.save(request);
    }
}
