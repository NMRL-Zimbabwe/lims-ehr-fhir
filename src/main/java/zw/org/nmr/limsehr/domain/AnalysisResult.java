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
    private boolean hidden;

    @JsonIgnore
    @Column(name = "sync_status")
    private String sync;

    public String getAnalysisResultId() {
        return analysisResultId;
    }

    public void setAnalysisResultId(String analysisResultId) {
        this.analysisResultId = analysisResultId;
    }

    public String getLaboratoryRequestId() {
        return laboratoryRequestId;
    }

    public void setLaboratoryRequestId(String laboratoryRequestId) {
        this.laboratoryRequestId = laboratoryRequestId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInterpretedResult() {
        return interpretedResult;
    }

    public void setInterpretedResult(String interpretedResult) {
        this.interpretedResult = interpretedResult;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getDateVerified() {
        return dateVerified;
    }

    public void setDateVerified(String dateVerified) {
        this.dateVerified = dateVerified;
    }

    public String getDateResultReceived() {
        return dateResultReceived;
    }

    public void setDateResultReceived(String dateResultReceived) {
        this.dateResultReceived = dateResultReceived;
    }

    public String getDateResultDispatched() {
        return dateResultDispatched;
    }

    public void setDateResultDispatched(String dateResultDispatched) {
        this.dateResultDispatched = dateResultDispatched;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    @Override
    public String toString() {
        return (
            "AnalysisResult [analysisResultId=" +
            analysisResultId +
            ", laboratoryRequestId=" +
            laboratoryRequestId +
            ", testId=" +
            testId +
            ", testName=" +
            testName +
            ", result=" +
            result +
            ", interpretedResult=" +
            interpretedResult +
            ", unit=" +
            unit +
            ", reviewState=" +
            reviewState +
            ", instrument=" +
            instrument +
            ", method=" +
            method +
            ", submittedBy=" +
            submittedBy +
            ", dateSubmitted=" +
            dateSubmitted +
            ", verifiedBy=" +
            verifiedBy +
            ", dateVerified=" +
            dateVerified +
            ", dateResultReceived=" +
            dateResultReceived +
            ", dateResultDispatched=" +
            dateResultDispatched +
            ", hidden=" +
            hidden +
            ", sync=" +
            sync +
            "]"
        );
    }
}
