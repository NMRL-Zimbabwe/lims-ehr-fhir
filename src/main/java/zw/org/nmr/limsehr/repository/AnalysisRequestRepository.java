package zw.org.nmr.limsehr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.AnalysisRequest;

/**
 * Spring Data JPA repository for the {@link AnalysisRequest} entity.
 */
@Repository
public interface AnalysisRequestRepository extends JpaRepository<AnalysisRequest, String> {
    AnalysisRequest findOneBymiddlewareAnalysisRequestUuid(String middleware_analysis_request_uuid);

    List<AnalysisRequest> findFirst10ByPatientResolvedIsNull();

    List<AnalysisRequest> findFirst10ByLaboratoryRequestResolvedIsNullAndPatientResolvedIsNotNull();

    List<AnalysisRequest> findFirst25ByPatientResolvedIsNull();

    List<AnalysisRequest> findFirst75ByPatientResolvedIsNull();
}
