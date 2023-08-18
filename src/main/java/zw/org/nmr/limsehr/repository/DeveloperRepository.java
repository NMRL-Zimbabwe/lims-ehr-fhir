package zw.org.nmr.limsehr.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Developer;

/**
 * Spring Data JPA repository for the Developer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {}
