package zw.org.nmr.limsehr.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.Client;
import zw.org.nmr.limsehr.repository.ClientRepository;
import zw.org.nmr.limsehr.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(DeveloperServiceImpl.class);
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        // TODO Auto-generated method stub
        return null;
    }

    void delete(Client client) {}

    @Override
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    public Optional<Client> findOne(String id) {
        log.debug("Request get client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public Client update(Client client) {
        log.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Page<Client> search(Pageable pageable, String text) {
        return clientRepository.findByNameContainingIgnoreCaseOrClientIdContainingIgnoreCase(pageable, text, text);
    }
}
