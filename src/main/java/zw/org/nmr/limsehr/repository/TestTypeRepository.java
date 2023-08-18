package zw.org.nmr.limsehr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Test;

/**
 * Spring Data JPA repository for the {@link Test} entity.
 */
@Repository
public interface TestTypeRepository extends JpaRepository<Test, String> {}
