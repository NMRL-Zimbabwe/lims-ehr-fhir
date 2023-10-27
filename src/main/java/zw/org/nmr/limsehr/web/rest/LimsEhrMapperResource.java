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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import zw.org.nmr.limsehr.domain.LimsEhrMapper;
import zw.org.nmr.limsehr.domain.LimsEhrMapper;
import zw.org.nmr.limsehr.service.LimsEhrMapperService;
import zw.org.nmr.limsehr.service.dto.EhrLImsMappingDTO;
import zw.org.nmr.limsehr.service.dto.MapperDataDTO;
import zw.org.nmr.limsehr.web.rest.errors.BadRequestAlertException;

/**RecordAlreadyExistException
 * REST controller for managing tests.
 * <p>
 * This class accesses the {@link LimsEhrMapper} entity, and needs to fetch its collection of authorities.
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
public class LimsEhrMapperResource {

    private final Logger log = LoggerFactory.getLogger(LimsEhrMapperResource.class);

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "ehrSampleTypeId", "ehrTestId", "limsSampleTypeId", "limsTestId")
    );

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    LimsEhrMapperService limsEhrMapperService;

    /**
     * {@code POST  /lims-ehr-mappings}  : Creates a new mapping.
     * <p>
     *
     * @param mapping the mapping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mapping, or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lims-ehr-mappings")
    public ResponseEntity<LimsEhrMapper> createMappings(@Valid @RequestBody LimsEhrMapper mapping) throws URISyntaxException {
        log.debug("REST request to save test : {}", mapping);

        if (mapping.getId() != null) {
            throw new BadRequestAlertException("A new test cannot already have an ID", "laboratoryManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else {
            LimsEhrMapper lTest = limsEhrMapperService.save(mapping);

            return ResponseEntity
                .created(new URI("/api/lims-ehr-mappings/" + lTest.getId()))
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.created", lTest.getId()))
                .body(lTest);
        }
    }

    /**
     * {@code PUT /lims-ehr-mappings} : Updates an existing mappings.
     *
     * @param labTest the test to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated test.
     */
    @PutMapping("/lims-ehr-mappings")
    public ResponseEntity<LimsEhrMapper> updateMappings(@Valid @RequestBody LimsEhrMapper labTest) {
        log.debug("REST request to update Laboratory : {}", labTest);
        LimsEhrMapper updatedTest = limsEhrMapperService.save(labTest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(updatedTest),
            HeaderUtil.createAlert(applicationName, "laboratoryManagement.updated", updatedTest.getId())
        );
    }

    /**
     * {@code GET /lims-ehr-mappings} : get all lime-ehr-mappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all mappings.
     */
    @GetMapping("/lims-ehr-mappings")
    public ResponseEntity<List<EhrLImsMappingDTO>> getAllMappings(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        final Page<EhrLImsMappingDTO> page = limsEhrMapperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /admin/lims-ehr-mappings/:mappingId} : get the "login" user.
     *
     * @param mappingId the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testId or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lims-ehr-mappings/{mappingId}")
    public ResponseEntity<LimsEhrMapper> getMappings(@PathVariable String mappingId) {
        log.debug("REST request to get mapping : {}", mappingId);
        return ResponseUtil.wrapOrNotFound(limsEhrMapperService.findById(mappingId));
    }

    /**
     * {@code GET /lims-ehr-mappings/data} : get all lime-ehr-mappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all mappings.
     */
    @GetMapping("/lims-ehr-mappings/data")
    public ResponseEntity<MapperDataDTO> getAllMappingsDaa(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        final MapperDataDTO data = limsEhrMapperService.getMapperData(pageable);
        return ResponseEntity.ok().body(data);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
