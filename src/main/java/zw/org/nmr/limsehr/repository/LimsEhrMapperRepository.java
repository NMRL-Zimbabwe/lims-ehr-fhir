package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.LimsEhrMapper;

/**
 * Spring Data JPA repository for the {@link LimsEhrMapper} entity.
 */
@Repository
public interface LimsEhrMapperRepository extends JpaRepository<LimsEhrMapper, String> {
    Optional<LimsEhrMapper> findByEhrSampleTypeIdAndEhrTestIdAndLaboratoryId(String sampleTypeId, String ehrTestCode, String labId);
    Optional<LimsEhrMapper> findByEhrSampleTypeIdAndEhrTestIdAndLaboratoryIdIsNull(String sampleTypeId, String ehrTestCode);
}
