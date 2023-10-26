package zw.org.nmr.limsehr.web.rest;

import jakarta.validation.Valid;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.domain.ReasonForTest;
import zw.org.nmr.limsehr.security.AuthoritiesConstants;
import zw.org.nmr.limsehr.service.ReasonForTestService;
import zw.org.nmr.limsehr.service.dto.ReasonForTestDTO;

/**
 * REST controller for managing patients.
 * <p>
 * This class accesses the {@link Patient} entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between Patient and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the patient and the authorities, because people will
 * quite often do relationships with the patient, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our patients'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages patients, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class ReasonForTestResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList(
            "id",
            "reasonForTestId",
            "name",
            "state",
            "loinc_code",
            "ehr_code",
            "createdBy",
            "createdDate",
            "lastModifiedBy",
            "lastModifiedDate"
        )
    );

    private final Logger log = LoggerFactory.getLogger(ReasonForTestResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private ReasonForTestService reasonForTestService;

    public ReasonForTestResource(ReasonForTestService reasonForTestService) {
        this.reasonForTestService = reasonForTestService;
    }

    /**
     * {@code PUT /reasonForTests} : Updates an existing ReasonForTest.
     *
     * @param reasonForTestDTO the ReasonForTest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patient.
     */
    @PutMapping("/reasonForTests")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ReasonForTest> updateReasonForTest(@Valid @RequestBody ReasonForTestDTO reasonForTestDTO) {
        log.debug("REST request to update ReasonForTest : {}", reasonForTestDTO);
        ReasonForTest updatedPatient = reasonForTestService.updateReasonForTest(reasonForTestDTO);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(updatedPatient),
            HeaderUtil.createAlert(applicationName, "patientManagement.updated", reasonForTestDTO.getId())
        );
    }

    /**
     * {@code GET /reasonForTests} : get all patients with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all reasonForTests.
     */
    @GetMapping("/reasonForTests")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<ReasonForTest>> getAllReasonForTests(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all ReasonForTest for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<ReasonForTest> page = reasonForTestService.getAllReasonForTests(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * {@code GET /ReasonForTest/:id} : get the "id" ReasonForTest.
     *
     * @param id the login of the ReasonForTest to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" ReasonForTest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reasonForTests/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ReasonForTest> getReasonForTest(@PathVariable String id) {
        log.debug("REST request to get Patient : {}", id);
        return ResponseUtil.wrapOrNotFound(reasonForTestService.getOne(id));
    }

    @GetMapping("/reasonForTests/search")
    public ResponseEntity<List<ReasonForTest>> searchReasonForTest(Pageable pageable, String text) {
        log.debug("REST request text serach : {}", text);
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<ReasonForTest> page = reasonForTestService.searchReasonForTest(pageable, text);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
