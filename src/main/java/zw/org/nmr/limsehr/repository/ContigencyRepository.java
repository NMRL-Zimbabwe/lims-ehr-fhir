package zw.org.nmr.limsehr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Contigency;

/**
 * Spring Data JPA repository for the {@link Sample} entity.
 */
@Repository
public interface ContigencyRepository extends JpaRepository<Contigency, String> {
    List<Contigency> findBySentToLimsIsNull();
}
