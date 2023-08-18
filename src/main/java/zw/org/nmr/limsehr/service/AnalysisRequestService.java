package zw.org.nmr.limsehr.service;

import java.time.LocalDate;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import zw.org.nmr.limsehr.domain.AnalysisRequest;
// import zw.org.nmr.limsehr.repository.AnalysisRequestRepository;
// import zw.org.nmr.limsehr.service.dto.MiddlewareAnalysisRequestDTO;
// import zw.org.nmr.limsehr.service.utility.DateUtility;

import zw.org.nmr.limsehr.domain.AnalysisRequest;
import zw.org.nmr.limsehr.repository.AnalysisRequestRepository;
import zw.org.nmr.limsehr.service.dto.MiddlewareAnalysisRequestDTO;
import zw.org.nmr.limsehr.service.utility.DateUtility;

/**
 * Service class for managing analysis request received from ehr-integration.
 */
@Service
@Transactional
public class AnalysisRequestService {

    private final Logger log = LoggerFactory.getLogger(AnalysisRequestService.class);

    private final AnalysisRequestRepository analysisRequestRepository;
    private final DateUtility dateUtility;

    public AnalysisRequestService(AnalysisRequestRepository analysisRequestRepository, DateUtility dateUtility) {
        this.analysisRequestRepository = analysisRequestRepository;
        this.dateUtility = dateUtility;
    }

    public AnalysisRequest createAnalysisRequest(MiddlewareAnalysisRequestDTO obj) {
        // middlewareAnalysisRequestUuid = Ehr primary key identifier (obj.getId())
        AnalysisRequest request = analysisRequestRepository.findOneBymiddlewareAnalysisRequestUuid(obj.getNotificationId());

        log.debug("**************************** analysis request : {}", obj.toString());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        if (request != null) {
            log.debug("The analysis request already exit: {}", request);
            return request;
        } else {
            analysisRequest.setAnalysisRequestId(UUID.randomUUID().toString());
            analysisRequest.setMiddlewareAnalysisRequestUuid(obj.getNotificationId()); // Ehr primary key identifier
            analysisRequest.setMiddlewareFacilityId(obj.getFacilityId()); // Ehr facility ID
            analysisRequest.setMiddlewareSampleId(obj.getSampleId()); // TODO remove duplicates here and follow liqui
            // base as well
            analysisRequest.setMiddlewareTestId(obj.getTestId());
            analysisRequest.setClientSampleId(obj.getReference());
            analysisRequest.setPatientId(obj.getPatientId());
            analysisRequest.setDateTested(LocalDate.now());
            analysisRequest.setSampleId(obj.getSampleId());
            analysisRequest.setTestId(obj.getTestId());
            analysisRequest.setLabId("LABID");
            analysisRequest.setLabName("LABNAME");

            log.debug("date collected: {}", obj.getDate());

            if (obj.getDate() != null) {
                analysisRequest.setDateCollected(dateUtility.stringToLocalDate(obj.getDate()));
            }

            log.debug("Created analysis request for id: {}", analysisRequest.getMiddlewareAnalysisRequestUuid());
            analysisRequestRepository.save(analysisRequest);
            return analysisRequest;
        }
    }

    public AnalysisRequest updateAnalysisRequest(AnalysisRequest ar) {
        log.info("AR ID: {}", ar.getAnalysisRequestId());
        // middlewareAnalysisRequestUuid = Ehr primary key identifier (obj.getId())
        AnalysisRequest analysisRequest = analysisRequestRepository.findOneBymiddlewareAnalysisRequestUuid(
            ar.getMiddlewareAnalysisRequestUuid()
        );

        if (analysisRequest == null) {
            log.info("The analysis request does not exit: {}", analysisRequest);
            throw new RecordDoesNotExistException();
        } else {
            log.info("Updated analysis request for id: {}", ar);

            AnalysisRequest saved = analysisRequestRepository.save(ar);

            log.info("Saved analysisRequest: {}", saved);

            return analysisRequest;
        }
    }
}
