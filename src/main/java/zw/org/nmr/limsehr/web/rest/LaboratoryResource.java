package zw.org.nmr.limsehr.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.repository.LaboratoryRepository;
import zw.org.nmr.limsehr.service.LaboratoryService;
import zw.org.nmr.limsehr.service.dto.LaboratoryDTO;
import zw.org.nmr.limsehr.web.rest.errors.BadRequestAlertException;
import zw.org.nmr.limsehr.web.rest.errors.EmailAlreadyUsedException;
import zw.org.nmr.limsehr.web.rest.errors.LoginAlreadyUsedException;
import zw.org.nmr.limsehr.web.rest.errors.RecordAlreadyExistException;

/**RecordAlreadyExistException
 * REST controller for managing users.
 * <p>
 * This class accesses the {@link Laboratory} entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between Laboratory and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class LaboratoryResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoryResource.class);

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "name", "code", "type")
    );

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratoryService laboratoryService;

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryResource(LaboratoryService laboratoryService, LaboratoryRepository laboratoryRepository) {
        this.laboratoryService = laboratoryService;
        this.laboratoryRepository = laboratoryRepository;
    }

    /**
     * {@code POST  /users}  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param labDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user, or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or email is already in use.
     */
    @PostMapping("/laboratories")
    public ResponseEntity<Laboratory> createLaboratory(@Valid @RequestBody LaboratoryDTO labDTO) throws URISyntaxException {
        log.debug("REST request to save Laboratory : {}", labDTO);

        if (labDTO.getId() != null) {
            throw new BadRequestAlertException("A new lab cannot already have an ID", "laboratoryManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (laboratoryRepository.findOneByCode(labDTO.getCode().toLowerCase()).isPresent()) {
            throw new RecordAlreadyExistException();
        } else {
            Laboratory lab = laboratoryService.saveLab(labDTO);

            return ResponseEntity
                .created(new URI("/api/laboratories/" + lab.getCode()))
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.created", lab.getCode()))
                .body(lab);
        }
    }

    /**
     * {@code PUT /laboratories} : Updates an existing Laboratory.
     *
     * @param labDTO the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already in use.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already in use.
     */
    @PutMapping("/update-laboratories")
    public ResponseEntity<LaboratoryDTO> updateLaboratory(@Valid @RequestBody LaboratoryDTO labDTO) {
        log.debug("REST request to update Laboratory : {}", labDTO);
        Optional<Laboratory> existingUser = laboratoryRepository.findOneByCodeIgnoreCase(labDTO.getCode());

        existingUser = laboratoryRepository.findOneByCode(labDTO.getCode());
        if (!existingUser.isPresent()) {
            throw new LoginAlreadyUsedException();
        }
        Optional<LaboratoryDTO> updatedLab = laboratoryService.updateLab(labDTO);

        return ResponseUtil.wrapOrNotFound(
            updatedLab,
            HeaderUtil.createAlert(applicationName, "laboratoryManagement.updated", labDTO.getCode())
        );
    }

    /**
     * {@code GET /users} : get all users.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/laboratories")
    public ResponseEntity<List<LaboratoryDTO>> getAllLabs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        final Page<LaboratoryDTO> page = laboratoryService.getAllLabs(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /admin/users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratories/{id}")
    public ResponseEntity<Laboratory> getLaboratory(@PathVariable String id) {
        log.debug("REST request to get laboratory : {}", id);
        return ResponseUtil.wrapOrNotFound(laboratoryService.getOne(id));
    }

    @GetMapping("/laboratories/search")
    public ResponseEntity<List<Laboratory>> searchLaboratory(Pageable pageable, String text) {
        log.debug("REST request text serach : {}", text);
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<Laboratory> page = laboratoryService.search(pageable, text);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
