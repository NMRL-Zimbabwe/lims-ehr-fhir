package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.EhrTest;

/**
 * Spring Data JPA repository for the {@link EhrTest} entity.
 */
@Repository
public interface EhrTestRepository extends JpaRepository<EhrTest, String> {
    Optional<EhrTest> findByEhrCode(String code);
}
