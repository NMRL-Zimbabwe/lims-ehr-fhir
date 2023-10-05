package zw.org.nmr.limsehr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import zw.org.nmr.limsehr.service.MyEntityWithListener;

/**
 * AnalysisResult
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(MyEntityWithListener.class)
@Table(name = "analysis_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnalysisResult extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "analysis_result_id", unique = true, nullable = false)
    private String analysisResultId;

    @JsonIgnore
    @Column(name = "laboratory_request_id")
    private String laboratoryRequestId;

    @JsonIgnore
    @Column(name = "test_id")
    private String testId;

    @JsonIgnore
    @Column(name = "test_name")
    private String testName;

    @JsonIgnore
    @Column(name = "result")
    private String result;

    @JsonIgnore
    @Column(name = "interpreted_result")
    private String interpretedResult;

    @JsonIgnore
    @Column(name = "unit")
    private String unit;

    @JsonIgnore
    @Column(name = "review_state")
    private String reviewState;

    @JsonIgnore
    @Column(name = "instrument")
    private String instrument;

    @JsonIgnore
    @Column(name = "method")
    private String method;

    @JsonIgnore
    @Column(name = "submitted_by")
    private String submittedBy;

    @JsonIgnore
    @Column(name = "date_submitted")
    private String dateSubmitted;

    @JsonIgnore
    @Column(name = "verified_by")
    private String verifiedBy;

    @JsonIgnore
    @Column(name = "date_verified")
    private String dateVerified;

    @JsonIgnore
    @Column(name = "date_result_received")
    private String dateResultReceived;

    @JsonIgnore
    @Column(name = "date_result_dispatched")
    private String dateResultDispatched;

    @JsonIgnore
    @Column(name = "hidden")
    private String hidden;

    @JsonIgnore
    @Column(name = "sync_status")
    private String sync;
}
