package zw.org.nmr.limsehr.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.service.dto.laboratory.request.LaboratoryRequestEhrDTO;

/**
 * Service class for managing users.
 */

public interface LaboratoryRequestService {
    Optional<LaboratoryRequest> getOne(String id);

    LaboratoryRequest saveLaboratoryRequest(LaboratoryRequestEhrDTO laboratoryRequestDTO, String patientId);

    Page<LaboratoryRequest> getAllLaboratoryRequest(Pageable pageable);

    Page<LaboratoryRequest> search(Pageable pageable, String text);

    void flushOurErrorsFromQueue(LaboratoryRequest request, String error_reason);
}
