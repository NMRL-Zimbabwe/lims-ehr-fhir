package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.SampleType;

/**
 * Spring Data JPA repository for the {@link SampleType} entity.
 */
@Repository
public interface SampleTypeRepository extends JpaRepository<SampleType, String> {
    Optional<SampleType> findBySampleTypeId(String sampleTypeId);
}
