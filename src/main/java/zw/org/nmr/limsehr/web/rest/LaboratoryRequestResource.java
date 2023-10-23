package zw.org.nmr.limsehr.web.rest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.service.LaboratoryRequestService;

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
public class LaboratoryRequestResource {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestResource.class);

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("clientSampleId", "sampleId", "laboratoryRequestId")
    );

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratoryRequestService laboratoryRequestService;

    private final LaboratoryRequestRepository laboratoryRequestRepository;

    public LaboratoryRequestResource(
        LaboratoryRequestService laboratoryRequestService,
        LaboratoryRequestRepository laboratoryRequestRepository
    ) {
        this.laboratoryRequestService = laboratoryRequestService;
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    /**
     * {@code GET /laboratoryRequests} : get all laboratoryRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/laboratoryRequests")
    public ResponseEntity<List<LaboratoryRequest>> getAllLabs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        final Page<LaboratoryRequest> page = laboratoryRequestService.getAllLaboratoryRequest(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /admin/users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratoryRequests/{id}")
    public ResponseEntity<LaboratoryRequest> getOne(@PathVariable String id) {
        log.debug("REST request to get User : {}", id);
        return ResponseUtil.wrapOrNotFound(laboratoryRequestService.getOne(id));
    }

    @GetMapping("/laboratoryRequests/search")
    public ResponseEntity<List<LaboratoryRequest>> searchLaboratory(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        String text
    ) {
        log.debug("REST request text serach : {}", text);
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<LaboratoryRequest> page = laboratoryRequestService.search(pageable, text);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
