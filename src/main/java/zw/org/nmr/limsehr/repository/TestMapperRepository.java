package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Test;
import zw.org.nmr.limsehr.domain.TestMapper;

/**
 * Spring Data JPA repository for the {@link Test} entity.
 */
@Repository
public interface TestMapperRepository extends JpaRepository<TestMapper, String> {
    Optional<TestMapper> findByLaboratoryIdAndEhrTestName(String labId, String ehrTestName);

    Optional<TestMapper> findByEhrTestNameAndLaboratoryIdIsNull(String ehrTestName);

    Optional<TestMapper> findByLaboratoryIdAndEhrTestNameAndSampleTypeId(String labId, String ehrTestName, String sampleTypeId);

    Optional<TestMapper> findByEhrTestNameAndSampleTypeIdAndLaboratoryIdIsNull(String ehrTestName, String sampleType);
}
