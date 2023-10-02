package zw.org.nmr.limsehr.service.sendToLims;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import zw.org.nmr.limsehr.repository.ClientRepository;
import zw.org.nmr.limsehr.repository.LaboratoryRepository;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.dto.laboratory.request.DTOforLIMS;
import zw.org.nmr.limsehr.service.dto.laboratory.request.SampleDTOforLIMS;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequest;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestAnalysisRequestDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestBatchDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestPatientDTO;
import zw.org.nmr.limsehr.service.enums.LaboratoryRequestStatus;

@Service
public class SendToLimsService {

    private static final Logger log = LoggerFactory.getLogger(SendToLimsService.class);

    @Autowired
    LaboratoryRequestRepository laboratoryRequestRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    LaboratoryRepository laboratoryRepository;

    @Autowired
    SendToLimsSampleResolver sendToLimsSampleResolver;

    @Autowired
    SendToLimsPatientResolver sendToLimsPatientResolver;

    @Autowired
    SendToLimsBatchResolver sendToLimsBatchResolver;

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

            UnifiedLimsRequestPatientDTO pt = sendToLimsPatientResolver.resolvePatient(patient, request);

            unifiedLimsRequest.setPatient(pt);

            /**
             * Construct case/batch details
             */

            /**   UnifiedLimsRequestBatchDTO analysisCase = new UnifiedLimsRequestBatchDTO();
            analysisCase.setCaseType("VL");
            analysisCase.setClientCaseID("");
            analysisCase.setReasonForVLtest("Routine Viral Load");
            analysisCase.setVLBreastFeeding(false);
            analysisCase.setVLPregnant(false);

            unifiedLimsRequest.setBatch(analysisCase);
         */

            UnifiedLimsRequestBatchDTO analysisCase = sendToLimsBatchResolver.resolveBatch();

            unifiedLimsRequest.setBatch(analysisCase);
            /**
             * Construct laboratory request details
             */

            UnifiedLimsRequestAnalysisRequestDTO analysisRequest = sendToLimsSampleResolver.resolveSample(request, destination);
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
