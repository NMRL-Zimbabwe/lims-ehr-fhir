package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.SampleType;
import zw.org.nmr.limsehr.domain.SampleTypeMapper;

/**
 * Spring Data JPA repository for the {@link SampleType} entity.
 */
@Repository
public interface SampleTypeMapperRepository extends JpaRepository<SampleTypeMapper, String> {
    Optional<SampleTypeMapper> findByLaboratoryIdAndEhrSampleTypeName(String labId, String ehrSampleTypeName);

    Optional<SampleTypeMapper> findByEhrSampleTypeNameAndLaboratoryIdIsNull(String ehrSampleTypeName);
}
