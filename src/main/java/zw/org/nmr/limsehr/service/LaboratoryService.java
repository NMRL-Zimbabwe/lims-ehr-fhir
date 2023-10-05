package zw.org.nmr.limsehr.service;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.service.dto.LaboratoryDTO;

/**
 * Service Interface for managing {@link Laboratory}.
 */

public interface LaboratoryService {
    Page<LaboratoryDTO> getAllLabs(Pageable pageable);

    Page<Laboratory> search(Pageable pageable, String text);

    Laboratory saveLab(@Valid LaboratoryDTO labDTO);

    Optional<LaboratoryDTO> updateLab(@Valid LaboratoryDTO labDTO);

    Optional<Laboratory> resolveByCode(String labId);

    Optional<Laboratory> getOne(String id);
}
