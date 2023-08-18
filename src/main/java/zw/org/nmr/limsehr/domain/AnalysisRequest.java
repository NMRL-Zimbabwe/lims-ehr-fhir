package zw.org.nmr.limsehr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import zw.org.nmr.limsehr.service.MyEntityWithListener;

/**
 * AnalysisRequest
 */
@Entity
@EntityListeners(MyEntityWithListener.class)
@Table(name = "analysis_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnalysisRequest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "analysis_request_id", unique = true, nullable = false)
    private String analysisRequestId;

    @Column(name = "lims_analysis_request_uuid")
    @JsonIgnore
    private String limsAnalysisRequestUuid;

    @Column(name = "middleware_analysis_request_uuid")
    @JsonIgnore
    private String middlewareAnalysisRequestUuid;

    @Column(name = "lab_id")
    @JsonIgnore
    private String labId;

    @Column(name = "lab_name")
    @JsonIgnore
    private String labName;

    @Column(name = "client_sample_id")
    @JsonIgnore
    private String clientSampleId;

    @Column(name = "patient_id")
    @JsonIgnore
    private String patientId;

    @Column(name = "middleware_sample_id")
    @JsonIgnore
    private String middlewareSampleId;

    @Column(name = "middleware_test_id")
    @JsonIgnore
    private String middlewareTestId;

    @Column(name = "middleware_facility_id")
    @JsonIgnore
    private String middlewareFacilityId;

    @Column(name = "facility_id")
    @JsonIgnore
    private String facilityId;

    @Column(name = "sample_id")
    @JsonIgnore
    private String sampleId;

    @Column(name = "test_id")
    @JsonIgnore
    private String testId;

    @JsonIgnore
    @Column(name = "lab_reference")
    private String labReference;

    @JsonIgnore
    private String result;

    @Column(name = "date_collected")
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCollected;

    @Column(name = "date_tested")
    @JsonIgnore
    @JsonFormat(pattern = "dd::MM::yyyy")
    private LocalDate dateTested;

    @Column(name = "review_state")
    @JsonIgnore
    private String reviewState;

    @JsonIgnore
    private String dispatched;

    @Column(name = "patient_resolved")
    @JsonIgnore
    private String patientResolved;

    @Column(name = "laboratory_request_resolved")
    @JsonIgnore
    private String laboratoryRequestResolved;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalysisRequest)) {
            return false;
        }
        return analysisRequestId != null && analysisRequestId.equals(((AnalysisRequest) o).analysisRequestId);
    }

    public String getAnalysisRequestId() {
        return analysisRequestId;
    }

    public void setAnalysisRequestId(String analysisRequestId) {
        this.analysisRequestId = analysisRequestId;
    }

    public String getLimsAnalysisRequestUuid() {
        return limsAnalysisRequestUuid;
    }

    public void setLimsAnalysisRequestUuid(String limsAnalysisRequestUuid) {
        this.limsAnalysisRequestUuid = limsAnalysisRequestUuid;
    }

    public String getMiddlewareAnalysisRequestUuid() {
        return middlewareAnalysisRequestUuid;
    }

    public void setMiddlewareAnalysisRequestUuid(String middlewareAnalysisRequestUuid) {
        this.middlewareAnalysisRequestUuid = middlewareAnalysisRequestUuid;
    }

    public String getClientSampleId() {
        return clientSampleId;
    }

    public void setClientSampleId(String clientSampleId) {
        this.clientSampleId = clientSampleId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMiddlewareSampleId() {
        return middlewareSampleId;
    }

    public void setMiddlewareSampleId(String middlewareSampleId) {
        this.middlewareSampleId = middlewareSampleId;
    }

    public String getMiddlewareTestId() {
        return middlewareTestId;
    }

    public void setMiddlewareTestId(String middlewareTestId) {
        this.middlewareTestId = middlewareTestId;
    }

    public String getMiddlewareFacilityId() {
        return middlewareFacilityId;
    }

    public void setMiddlewareFacilityId(String middlewareFacilityId) {
        this.middlewareFacilityId = middlewareFacilityId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getLabReference() {
        return labReference;
    }

    public void setLabReference(String labReference) {
        this.labReference = labReference;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(LocalDate dateCollected) {
        this.dateCollected = dateCollected;
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    public String getDispatched() {
        return dispatched;
    }

    public void setDispatched(String dispatched) {
        this.dispatched = dispatched;
    }

    public LocalDate getDateTested() {
        return dateTested;
    }

    public void setDateTested(LocalDate dateTested) {
        this.dateTested = dateTested;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getPatientResolved() {
        return patientResolved;
    }

    public void setPatientResolved(String patientResolved) {
        this.patientResolved = patientResolved;
    }

    public String getLaboratoryRequestResolved() {
        return laboratoryRequestResolved;
    }

    public void setLaboratoryRequestResolved(String laboratoryRequestResolved) {
        this.laboratoryRequestResolved = laboratoryRequestResolved;
    }

    @PostUpdate
    private void resetSyncTime() {
        System.out.println("Analysis Request called in entity: " + getAnalysisRequestId());
    }

    @Override
    public String toString() {
        return (
            "AnalysisRequest [analysisRequestId=" +
            analysisRequestId +
            ", limsAnalysisRequestUuid=" +
            limsAnalysisRequestUuid +
            ", middlewareAnalysisRequestUuid=" +
            middlewareAnalysisRequestUuid +
            ", labId=" +
            labId +
            ", labName=" +
            labName +
            ", clientSampleId=" +
            clientSampleId +
            ", patientId=" +
            patientId +
            ", middlewareSampleId=" +
            middlewareSampleId +
            ", middlewareTestId=" +
            middlewareTestId +
            ", middlewareFacilityId=" +
            middlewareFacilityId +
            ", facilityId=" +
            facilityId +
            ", sampleId=" +
            sampleId +
            ", testId=" +
            testId +
            ", labReference=" +
            labReference +
            ", result=" +
            result +
            ", dateCollected=" +
            dateCollected +
            ", dateTested=" +
            dateTested +
            ", reviewState=" +
            reviewState +
            ", dispatched=" +
            dispatched +
            ", patientResolved=" +
            patientResolved +
            ", laboratoryRequestResolved=" +
            laboratoryRequestResolved +
            "]"
        );
    }
}
