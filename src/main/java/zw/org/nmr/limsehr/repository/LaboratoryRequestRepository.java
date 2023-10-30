package zw.org.nmr.limsehr.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;

/**
 * Spring Data JPA repository for the {@link LaboratoryRequest} entity.
 */
@Repository
public interface LaboratoryRequestRepository extends JpaRepository<LaboratoryRequest, String> {
    List<LaboratoryRequest> findBySentToEhrIsNull();

    LaboratoryRequest findByMiddlewareAnalysisRequestUuid(String id);

    List<LaboratoryRequest> findBySentToLimsIsNull();

    List<LaboratoryRequest> findFirst10BySentToEhrIsNullAndResultNotNull();

    List<LaboratoryRequest> findFirst10BySentToEhrIsNullAndAcknowledgeRecordReceiptIsNull();

    List<LaboratoryRequest> findBySentToLimsIsNotNull();

    LaboratoryRequest findByClientSampleId(String clientSampleId);

    List<LaboratoryRequest> findFirst10BySentToEhrIsNullAndAcknowledgeSampleReceiptIsNotNull();

    List<LaboratoryRequest> findFirst10BySentToEhrIsNullAndStatus(String rejected);

    List<LaboratoryRequest> findBySentToEhrIsNullAndResultNotNull();

    List<LaboratoryRequest> findBySentToLimsIsNullAndRetryLessThan(int retry);
    /*
     * LaboratoryRequest findOneBymiddlewareAnalysisRequestUuid(String
     * middleware_analysis_request_uuid);
     *
     * List<LaboratoryRequest> findByResolvedIsNull();
     *
     * List<LaboratoryRequest> findFirst10ByResolvedIsNull();
     *
     * List<LaboratoryRequest> findFirst10ByResolvedIsNotNull();
     */

    List<LaboratoryRequest> findByResultStatusIsNullAndResultNotNull();

    LaboratoryRequest findByLabReferenceSampleId(String sampleId);

    List<LaboratoryRequest> findByReviewStateAndResultStatusIsNull(String published);

    List<LaboratoryRequest> findByReviewStateIsNullAndResultStatusIsNull();

    List<LaboratoryRequest> findByResultIsNotNullAndResultStatusIsNull();

    Page<LaboratoryRequest> findByClientSampleIdContainingIgnoreCaseOrSampleIdContainingIgnoreCase(
        Pageable pageable,
        String text,
        String text2
    );

    long countBySampleIdIsNotNull();

    List<LaboratoryRequest> findByLabReferenceSampleIdIsNotNullAndSentToEhrIsNull();
}
