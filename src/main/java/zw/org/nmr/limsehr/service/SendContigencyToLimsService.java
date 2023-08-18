package zw.org.nmr.limsehr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.Client;
import zw.org.nmr.limsehr.domain.Contigency;
import zw.org.nmr.limsehr.repository.ClientRepository;
import zw.org.nmr.limsehr.repository.ContigencyRepository;
import zw.org.nmr.limsehr.repository.PatientAddressRepository;
import zw.org.nmr.limsehr.repository.PatientIdentifierRepository;
import zw.org.nmr.limsehr.repository.PatientPhoneRepository;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequest;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestAnalysisRequestDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestBatchDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestPatientDTO;
import zw.org.nmr.limsehr.service.enums.LaboratoryRequestStatus;

@Service
public class SendContigencyToLimsService {

    private static final Logger log = LoggerFactory.getLogger(SendContigencyToLimsService.class);

    @Autowired
    ContigencyRepository contigencyRepository;

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

    // @Autowired
    // @Qualifier(value = "senaiteContainerFactory")
    // private AmqpTemplate amqpTemplate;

    public void sendMessageToLims(UnifiedLimsRequest message) throws JsonProcessingException {
        System.out.println("[******] Waiting for messages.");

        ObjectMapper mapper = new ObjectMapper();
        Object jsonMessage = mapper.writeValueAsString(message);
        // amqpTemplate.convertAndSend("ehr-lims", "nmrl", jsonMessage);
        // amqpTemplate.convertAndSend(jsonMessage);

    }

    public void sendMessageToLimsa(UnifiedLimsRequest message) throws Exception {
        /*
         * ConnectionFactory factory = new ConnectionFactory();
         * factory.setHost("196.27.127.58"); factory.setPort(4056);
         * factory.setVirtualHost("/"); factory.setUsername("test");
         * factory.setPassword("test");
         *
         * try (Connection connection = factory.newConnection(); Channel channel =
         * connection.createChannel()) { // channel.queueDeclare(QUEUE_NAME, true,
         * false, false, null); // channel.exchangeBind("my_exchange", "", "test"); //
         * channel.exchangeDeclare("", "direct"); final String DYNAMIC_QUEUE_NAME =
         * message.getLabId(); channel.queueBind(DYNAMIC_QUEUE_NAME, "ehr-lims",
         * "nmrl");
         *
         * channel.QueueDeclare(queue: queueName, durable: true, exclusive: false,
         * autoDelete: false, arguments: null);
         *
         *
         * ObjectMapper mapper = new ObjectMapper(); Object jsonMessage =
         * mapper.writeValueAsString(message);
         *
         * channel.basicPublish("", DYNAMIC_QUEUE_NAME, null,
         * jsonMessage.toString().getBytes()); System.out.println(" [x] Sent '" +
         * message.toString().getBytes(StandardCharsets.UTF_8) + "'"); }
         */
    }

    /*
     * public void sendMessage(UnifiedLimsRequest unifiedLimsRequest) {
     * System.out.println("[******] Waiting for messages."); //
     * amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, unifiedLimsRequest); }
     */

    @SuppressWarnings({ "null", "unused" })
    //@Scheduled(fixedRate = 15000)
    public void requestBuilder() throws Exception {
        /**
         * Search for records that have not been sent to LIMS
         */

        UnifiedLimsRequest unifiedLimsRequest = new UnifiedLimsRequest();

        List<Contigency> sendToLims = contigencyRepository.findBySentToLimsIsNull();
        for (Contigency request : sendToLims) {
            //Patient patient = patientRepository.findByPatientId(request.getPatient().getPatientId());

            /**
             * Construct patient details
             */

            unifiedLimsRequest.setLabId("BRIDH");
            unifiedLimsRequest.setLabName("BRIDH");
            UnifiedLimsRequestPatientDTO pt = new UnifiedLimsRequestPatientDTO();

            pt.setFirstname(request.getName());
            pt.setSurname(request.getSurname());

            log.debug("patient information : {}", request.getSurname());

            log.debug("client information information : {}", request.getClientName());

            Client client = clientRepository.findByNameIgnoreCase(request.getClientName());

            if (client != null) {
                pt.setClientID(client.getClientId());
                log.debug("client information : {}", client);
            } else {
                return;
            }

            log.debug("[******] after clent : {}", request.getClientName());
            pt.setClientPatientID(request.getClientPatientId());
            pt.setGender(request.getGender());

            if (request.getDobEstimated().equals("Yes")) {
                pt.setBirthDateEstimated(true);
            }

            pt.setBirthDate(request.getDob());

            //PatientPhone phone = phoneRepository
            //.findFirstByPatientIdOrderByLastModifiedDateDesc(request.getPatient().getPatientId());

            /**
             * If patient record has a corresponding phone number, add it to the record.
             * Assumption -> all patients with phone numbers have consented to SMS
             */

            if (request.getPhone_number() != null) {
                pt.setMobilePhone(request.getPhone_number());

                if (request.getConsentToSms().equals("Yes")) {
                    pt.setConsentSMS(true);
                }
            } else {
                pt.setMobilePhone("");
            }

            /**
             * If the patient has got corresponding identifiers, add them to the patient
             * record.
             */

            //Collection<PatientIdentifier> patientIdentifiers = patientIdentifierRepository
            //	.findByPatientId(patient.getPatientId());

            //UnifiedLimsRequestPatientIdentifiersDTO identification[] = new UnifiedLimsRequestPatientIdentifiersDTO[patientIdentifiers
            //		.size()];

            /*
             * int index = 0; for (PatientIdentifier identifier : patientIdentifiers) {
             *
             * if (identifier.getNumber() != null) { String type = "National ID";
             *
             * if (identifier.getType() != null) { type = identifier.getType(); }
             * log.debug("test identifier {}", identifier);
             *
             * identification[index++] = new
             * UnifiedLimsRequestPatientIdentifiersDTO(identifier.getNumber(),
             * "National ID");
             *
             * } }
             *
             * pt.setPatientIdentifiers(identification);
             */

            /**
             * If patient has an address add it to the patient record
             */

            /*
             * PatientAddress patientAddress =
             * patientAddressRepository.findByPatientId(patient.getPatientId());
             *
             * if (patientAddress != null) {
             *
             * UnifiedLimsRequestCountryState address = new
             * UnifiedLimsRequestCountryState();
             *
             * address.setCountry("Zimbabwe"); address.setState("Harare");
             * address.setDistrict("Harare"); pt.setCountryState(address); }
             */

            unifiedLimsRequest.setPatient(pt);

            /**
             * Construct case/batch details
             */

            UnifiedLimsRequestBatchDTO analysisCase = new UnifiedLimsRequestBatchDTO();
            analysisCase.setCaseType(request.getCaseType());
            analysisCase.setClientCaseID("");
            analysisCase.setReasonForVLtest(request.getReasonForVlTest());
            analysisCase.setVLBreastFeeding(false);
            analysisCase.setVLPregnant(false);

            unifiedLimsRequest.setBatch(analysisCase);

            /**
             * Construct laboratory request details
             */

            UnifiedLimsRequestAnalysisRequestDTO analysisRequest = new UnifiedLimsRequestAnalysisRequestDTO();

            analysisRequest.setDateSampled(request.getDateSampled());

            String profiles[] = new String[] { request.getAnalysisProfiles() };

            analysisRequest.setProfiles(profiles);
            analysisRequest.setSampleType(request.getSampleType());
            analysisRequest.setContact(request.getClientContact());

            if (request.getClientSampleId() != null) {
                analysisRequest.setClientSampleId(request.getClientSampleId());
            }

            analysisRequest.setTemplate("");
            unifiedLimsRequest.setAnalysisRequest(analysisRequest);
            log.debug("[******] after AnalysisRequest : {}", unifiedLimsRequest);
            // The code below will be substituted with the bundled request

            /*
             * DTOforLIMS builder = new DTOforLIMS();
             *
             * builder.setFirstname(patient.getFirstname());
             * builder.setSurname(patient.getLastname());
             * builder.setGender(patient.getGender());
             * builder.setBirthDate(patient.getDob().toString());
             *
             * Client client = clientRepository.findByClientId(request.getClientId());
             *
             * builder.setPrimaryReferrer(client.getId());
             *
             * log.debug("Client information {}", client);
             *
             * builder.setParent_path(client.getPath()); builder.setPortal_type("Patient");
             * if (patient.getArt() != null) {
             * builder.setClientPatientId(patient.getArt().replace("-", "")); } else { //
             * builder.setClientPatientId(patient.getArt().replace("-", ""));
             *
             * }
             *
             * SampleDTOforLIMS sample = new SampleDTOforLIMS();
             * sample.setDateSampled(request.getDateCollected().toString());
             * sample.setPortal_type("AnalysisRequest");
             * sample.setParent_path(client.getPath());
             *
             * sample.setClientSampleId(request.getClientSampleId());
             *
             * // TODO Map actual values corresponding to EHR Integration
             * sample.setPatient(request.getPatient().getPatientId());
             * sample.setProfiles("65a688c6a45e4bc39511c0a824bf3572");
             * sample.setSampleType("306d0f4aefa9452d9e2e936915b1e5ca");
             *
             * builder.setSample(sample);
             */

            if (request.getClientSampleId() != null) {
                sendMessageToLims(unifiedLimsRequest); // Flag sent request as sent to LIMS
                request.setSentToLims(LaboratoryRequestStatus.SENT_TO_LIMS.toString());
                contigencyRepository.save(request);
            } else {
                /**
                 * if analysis request does not contain Client patientID reject for now. LIMS is
                 * currently using OIART numbers as identifier so null values will be ignored
                 *
                 */
                //request.setStatus(LaboratoryRequestStatus.REJECTED.toString());
                //request.setSentToLims(LaboratoryRequestStatus.DECLINED.toString());
                request.setReceivedInLims("Rejected No Client Patient ID found");
                contigencyRepository.save(request);
            }
        }
        log.debug("[******] after unifiedLimsRequest : {}", unifiedLimsRequest);
        /*
         * if (unifiedLimsRequest.getPatient() != null) {
         * log.debug("unifiedLimsRequest information -> : {}", unifiedLimsRequest);
         * sendMessageToLims(unifiedLimsRequest); }
         */

    }
}
