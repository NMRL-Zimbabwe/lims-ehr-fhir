package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Laboratory;

/**
 * Spring Data JPA repository for the {@link Laboratory} entity.
 */
@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, String> {
    boolean existsByCode(String code);

    Optional<Laboratory> findByCode(String code);

    Optional<Laboratory> findOneByCode(String code);

    Optional<Laboratory> findOneByCodeIgnoreCase(String email);

    Page<Laboratory> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(Pageable pageable, String text, String text2);

    Optional<Laboratory> findByEhrCode(String ehrLabCode);

    Optional<Laboratory> findByEhrCodeOrCode(String code1, String code2);
}
