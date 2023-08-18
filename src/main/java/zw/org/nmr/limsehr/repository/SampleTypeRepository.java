package zw.org.nmr.limsehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Sample;

/**
 * Spring Data JPA repository for the {@link Sample} entity.
 */
@Repository
public interface SampleTypeRepository extends JpaRepository<Sample, String> {}
