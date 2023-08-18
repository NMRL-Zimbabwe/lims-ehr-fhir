package zw.org.nmr.limsehr.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import java.time.LocalDate;

/**
 * A DTO representing a user, with his authorities.
 */
public class AnalysisRequestDTO {

    @Id
    private String analysisRequestId;

    @JsonIgnore
    private String limsAnalysisRequestUuid;

    @JsonIgnore
    private String middlewareAnalysisRequestUuid;

    @JsonIgnore
    private String labId;

    @JsonIgnore
    private String labName;

    @JsonIgnore
    private String clientSampleId;

    @JsonIgnore
    private String patientId;

    @JsonIgnore
    private String middlewareSampleId;

    @JsonIgnore
    private String middlewareTestId;

    @JsonIgnore
    private String middlewareFacilityId;

    @JsonIgnore
    private String sampleId;

    @JsonIgnore
    private String testId;

    @JsonIgnore
    private String labReference;

    @JsonIgnore
    private String result;

    @JsonIgnore
    @JsonFormat(pattern = "dd::MM::yyyy")
    private LocalDate dateCollected;

    @JsonIgnore
    private String reviewState;

    @JsonIgnore
    private String dispatched;

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateTested;

    public AnalysisRequestDTO() {
        // Empty constructor needed for Jackson.
    }

    public AnalysisRequestDTO(
        String analysisRequestId,
        String limsAnalysisRequestUuid,
        String middlewareAnalysisRequestUuid,
        String clientSampleId,
        String patientId,
        String middlewareSampleId,
        String middlewareTestId,
        String middlewareFacilityId,
        String sampleId,
        String testId,
        String labReference,
        String result,
        LocalDate dateCollected,
        String reviewState,
        String dispatched,
        LocalDate dateTested,
        String labId,
        String labName
    ) {
        super();
        this.analysisRequestId = analysisRequestId;
        this.limsAnalysisRequestUuid = limsAnalysisRequestUuid;
        this.middlewareAnalysisRequestUuid = middlewareAnalysisRequestUuid;
        this.clientSampleId = clientSampleId;
        this.patientId = patientId;
        this.middlewareSampleId = middlewareSampleId;
        this.middlewareTestId = middlewareTestId;
        this.middlewareFacilityId = middlewareFacilityId;
        this.sampleId = sampleId;
        this.testId = testId;
        this.labReference = labReference;
        this.result = result;
        this.dateCollected = dateCollected;
        this.reviewState = reviewState;
        this.dispatched = dispatched;
        this.dateTested = dateTested;
        this.labId = labId;
        this.labName = labName;
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

    public void setLimsAnalysisRequest_uuid(String limsAnalysisRequest_uuid) {
        this.limsAnalysisRequestUuid = limsAnalysisRequest_uuid;
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

    public void setDateCollected(LocalDate localDate) {
        this.dateCollected = localDate;
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

    public void setLimsAnalysisRequestUuid(String limsAnalysisRequestUuid) {
        this.limsAnalysisRequestUuid = limsAnalysisRequestUuid;
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

    @Override
    public String toString() {
        return (
            "AnalysisRequestDTO [analysisRequestId=" +
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
            ", reviewState=" +
            reviewState +
            ", dispatched=" +
            dispatched +
            ", dateTested=" +
            dateTested +
            "]"
        );
    }
}
