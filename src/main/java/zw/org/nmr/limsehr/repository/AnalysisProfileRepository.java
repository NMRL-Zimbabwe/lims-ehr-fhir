package zw.org.nmr.limsehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.AnalysisProfile;

/**
 * Spring Data JPA repository for the {@link AnalysisProfile} entity.
 */
@Repository
public interface AnalysisProfileRepository extends JpaRepository<AnalysisProfile, String> {}
