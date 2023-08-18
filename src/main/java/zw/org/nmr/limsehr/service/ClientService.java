package zw.org.nmr.limsehr.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.org.nmr.limsehr.domain.Client;

/**
 * Service Interface for managing {@link Client}.
 */
public interface ClientService {
    Client save(Client client);

    Page<Client> findAll(Pageable pageable);

    Optional<Client> findOne(String id);

    Client update(Client client);

    Page<Client> search(Pageable pageable, String text);
}
