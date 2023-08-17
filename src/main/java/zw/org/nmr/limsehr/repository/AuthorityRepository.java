package zw.org.nmr.limsehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.org.nmr.limsehr.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
