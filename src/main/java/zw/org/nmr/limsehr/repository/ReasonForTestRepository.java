package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.domain.ReasonForTest;

/**
 * Spring Data JPA repository for the {@link Patient} entity.
 */
@Repository
public interface ReasonForTestRepository extends JpaRepository<ReasonForTest, String> {
    ReasonForTest findOneByReasonForTestId(String ReasonForTestId);

    Optional<ReasonForTest> findByReasonForTestId(String ReasonForTestId);

    Page<ReasonForTest> findByNameContainingIgnoreCaseOrReasonForTestIdContainingIgnoreCase(Pageable pageable, String text, String text2);
}
