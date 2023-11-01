package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.EhrSampleType;

/**
 * Spring Data JPA repository for the {@link EhrSampleType} entity.
 */
@Repository
public interface EhrSampleTypeRepository extends JpaRepository<EhrSampleType, String> {
    Optional<EhrSampleType> findByEhrCode(String code);
}
