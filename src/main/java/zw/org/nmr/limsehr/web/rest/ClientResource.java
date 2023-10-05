package zw.org.nmr.limsehr.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import zw.org.nmr.limsehr.domain.Client;
import zw.org.nmr.limsehr.repository.ClientRepository;
import zw.org.nmr.limsehr.service.ClientService;
import zw.org.nmr.limsehr.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "name", "clientId", "phone")
    );

    private static final String ENTITY_NAME = "client";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@code POST  /developers} : Create a new developer.
     *
     * @param client the developer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new developer, or with status {@code 400 (Bad Request)} if
     *         the developer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to save Developer : {}", client);
        if (client.getId() != null) {
            throw new BadRequestAlertException("A new developer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Client result = clientService.save(client);
        return ResponseEntity
            .created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clients} : get all the clients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of developers in body.
     */
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Developers");
        Page<Client> page = clientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable String id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = clientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id", required = false) final String id, @RequestBody Client client)
        throws URISyntaxException {
        log.debug("REST request to update Client : {}, {}", id, client);
        if (client.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, client.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Client result = clientService.update(client);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, client.getId().toString()))
            .body(result);
    }

    @GetMapping("/clients/search")
    public ResponseEntity<List<Client>> searchClient(Pageable pageable, String text) {
        log.debug("REST request text serach : {}", text);
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<Client> page = clientService.search(pageable, text);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
