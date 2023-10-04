package zw.org.nmr.limsehr.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.org.nmr.limsehr.domain.Client;

/**
 * Spring Data JPA repository for the {@link Client} entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String facilityId);

    Client findByNameIgnoreCase(String name);

    Client findByClientIdAndParentPath(String clientId, String clientStatus);

    Page<Client> findByNameContainingIgnoreCaseOrClientIdContainingIgnoreCase(Pageable pageable, String text, String text2);
}
