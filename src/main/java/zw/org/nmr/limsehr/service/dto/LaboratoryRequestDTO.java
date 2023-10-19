package zw.org.nmr.limsehr.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import zw.org.nmr.limsehr.domain.Patient;

/**
 * Analysis request
 */

public class LaboratoryRequestDTO {

    private static final long serialVersionUID = 1L;

    private String laboratoryRequestId;

    private String limsAnalysisRequestUuid;

    private String middlewareAnalysisRequestUuid;

    private String labId;

    private String labName;

    private String clientSampleId;

    private Patient patient;

    private String middlewareSampleId;

    private String middleware_test_id;

    private String middlewareFacilityId;

    private String facilityId;

    private String sampleId;

    private String testId;

    private String labReferenceSampleId;

    private String result;

    private String unit;

    private String remarks;

    private LocalDate dateCollected;

    @JsonFormat(pattern = "dd::MM::yyyy")
    private LocalDate dateTested;

    @JsonIgnore
    private String reviewState;

    @JsonIgnore
    private String dispatched;

    @JsonIgnore
    private String sentToLims;

    @JsonIgnore
    private String sentToEhr;

    @JsonIgnore
    private int retry;

    @JsonIgnore
    private String errorReason;

    @JsonIgnore
    private String resultStatus;

    @JsonIgnore
    private LocalDate dateResultReceivedFromLims;

    public LaboratoryRequestDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public LaboratoryRequestDTO(
        String laboratoryRequestId,
        String limsAnalysisRequestUuid,
        String middlewareAnalysisRequestUuid,
        String labId,
        String labName,
        String clientSampleId,
        Patient patient,
        String middlewareSampleId,
        String middleware_test_id,
        String middlewareFacilityId,
        String facilityId,
        String sampleId,
        String testId,
        String labReferenceSampleId,
        String result,
        String unit,
        String remarks,
        LocalDate dateCollected,
        LocalDate dateTested,
        String reviewState,
        String dispatched,
        String sentToLims,
        String sentToEhr,
        int retry,
        String errorReason,
        String resultStatus,
        LocalDate dateResultReceivedFromLims
    ) {
        super();
        this.laboratoryRequestId = laboratoryRequestId;
        this.limsAnalysisRequestUuid = limsAnalysisRequestUuid;
        this.middlewareAnalysisRequestUuid = middlewareAnalysisRequestUuid;
        this.labId = labId;
        this.labName = labName;
        this.clientSampleId = clientSampleId;
        this.patient = patient;
        this.middlewareSampleId = middlewareSampleId;
        this.middleware_test_id = middleware_test_id;
        this.middlewareFacilityId = middlewareFacilityId;
        this.facilityId = facilityId;
        this.sampleId = sampleId;
        this.testId = testId;
        this.labReferenceSampleId = labReferenceSampleId;
        this.result = result;
        this.unit = unit;
        this.remarks = remarks;
        this.dateCollected = dateCollected;
        this.dateTested = dateTested;
        this.reviewState = reviewState;
        this.dispatched = dispatched;
        this.sentToLims = sentToLims;
        this.sentToEhr = sentToEhr;
        this.retry = retry;
        this.errorReason = errorReason;
        this.resultStatus = resultStatus;
        this.dateResultReceivedFromLims = dateResultReceivedFromLims;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaboratoryRequestDTO)) {
            return false;
        }
        return laboratoryRequestId != null && laboratoryRequestId.equals(((LaboratoryRequestDTO) o).laboratoryRequestId);
    }

    public String getLaboratoryRequestId() {
        return laboratoryRequestId;
    }

    public void setLaboratoryRequestId(String laboratoryRequestId) {
        this.laboratoryRequestId = laboratoryRequestId;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getMiddlewareSampleId() {
        return middlewareSampleId;
    }

    public void setMiddlewareSampleId(String middlewareSampleId) {
        this.middlewareSampleId = middlewareSampleId;
    }

    public String getMiddleware_test_id() {
        return middleware_test_id;
    }

    public void setMiddleware_test_id(String middleware_test_id) {
        this.middleware_test_id = middleware_test_id;
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

    public String getLabReferenceSampleId() {
        return labReferenceSampleId;
    }

    public void setLabReferenceSampleId(String labReferenceSampleId) {
        this.labReferenceSampleId = labReferenceSampleId;
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

    public LocalDate getDateTested() {
        return dateTested;
    }

    public void setDateTested(LocalDate dateTested) {
        this.dateTested = dateTested;
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

    public String getSentToLims() {
        return sentToLims;
    }

    public void setSentToLims(String sentToLims) {
        this.sentToLims = sentToLims;
    }

    public String getSentToEhr() {
        return sentToEhr;
    }

    public void setSentToEhr(String sentToEhr) {
        this.sentToEhr = sentToEhr;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public LocalDate getDateResultReceivedFromLims() {
        return dateResultReceivedFromLims;
    }

    public void setDateResultReceivedFromLims(LocalDate dateResultReceivedFromLims) {
        this.dateResultReceivedFromLims = dateResultReceivedFromLims;
    }

    @Override
    public String toString() {
        return (
            "LaboratoryRequestDTO [laboratoryRequestId=" +
            laboratoryRequestId +
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
            ", patient=" +
            patient +
            ", middlewareSampleId=" +
            middlewareSampleId +
            ", middleware_test_id=" +
            middleware_test_id +
            ", middlewareFacilityId=" +
            middlewareFacilityId +
            ", facilityId=" +
            facilityId +
            ", sampleId=" +
            sampleId +
            ", testId=" +
            testId +
            ", labReferenceSampleId=" +
            labReferenceSampleId +
            ", result=" +
            result +
            ", unit=" +
            unit +
            ", remarks=" +
            remarks +
            ", dateCollected=" +
            dateCollected +
            ", dateTested=" +
            dateTested +
            ", reviewState=" +
            reviewState +
            ", dispatched=" +
            dispatched +
            ", sentToLims=" +
            sentToLims +
            ", sentToEhr=" +
            sentToEhr +
            ", retry=" +
            retry +
            ", errorReason=" +
            errorReason +
            ", resultStatus=" +
            resultStatus +
            ", dateResultReceivedFromLims=" +
            dateResultReceivedFromLims +
            "]"
        );
    }
}
