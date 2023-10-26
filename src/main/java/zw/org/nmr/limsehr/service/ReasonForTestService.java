package zw.org.nmr.limsehr.service;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.org.nmr.limsehr.domain.ReasonForTest;
import zw.org.nmr.limsehr.service.dto.ReasonForTestDTO;

/**
 * Service class for managing reasonForVlTest.
 */

public interface ReasonForTestService {
    Page<ReasonForTest> getAllReasonForTests(Pageable pageable);

    ReasonForTest saveReasonForTest(@Valid ReasonForTest reasonForTest);

    ReasonForTest updateReasonForTest(@Valid ReasonForTestDTO reasonForTestDTO);

    Page<ReasonForTest> searchReasonForTest(Pageable pageable, String text);

    Optional<ReasonForTest> getOne(String reasonForTestId);
}
