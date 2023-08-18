package zw.org.nmr.limsehr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Analysis request
 */
@Entity
@Table(name = "laboratory_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LaboratoryRequest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "laboratory_request_id", unique = true, nullable = false)
    private String laboratoryRequestId;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_laboratory_request_patient"))
    private Patient patient;

    @Column(name = "middleware_client_uuid")
    @JsonIgnore
    private String middlewareClientUuid;

    @Column(name = "client_id")
    @JsonIgnore
    private String clientId;

    @JsonIgnore
    private String client;

    @Column(name = "sample_id")
    @JsonIgnore
    private String sampleId;

    @Column(name = "test_id")
    @JsonIgnore
    private String testId;

    @JsonIgnore
    @Column(name = "lab_reference_sample_id")
    private String labReferenceSampleId;

    @JsonIgnore
    private String result;

    @JsonIgnore
    private String unit;

    @JsonIgnore
    private String remarks;

    @JsonIgnore
    private int retry;

    @JsonIgnore
    @Column(name = "error_reason")
    private String errorReason;

    @JsonIgnore
    @Column(name = "acknowledge_sample_receipt")
    private String acknowledgeSampleReceipt;

    @JsonIgnore
    @Column(name = "acknowledge_record_receipt")
    private String acknowledgeRecordReceipt;

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

    private String status;

    @Column(name = "sent_to_lims")
    @JsonIgnore
    private String sentToLims;

    @Column(name = "sent_to_ehr")
    @JsonIgnore
    private String sentToEhr;

    @Column(name = "result_status")
    private String resultStatus;

    @Column(name = "date_result_received_from_lims")
    private LocalDate dateResultReceivedFromLims;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaboratoryRequest)) {
            return false;
        }
        return laboratoryRequestId != null && laboratoryRequestId.equals(((LaboratoryRequest) o).laboratoryRequestId);
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

    public String getMiddlewareClientUuid() {
        return middlewareClientUuid;
    }

    public void setMiddlewareClientUuid(String middlewareClientUuid) {
        this.middlewareClientUuid = middlewareClientUuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public String getAcknowledgeSampleReceipt() {
        return acknowledgeSampleReceipt;
    }

    public void setAcknowledgeSampleReceipt(String acknowledgeSampleReceipt) {
        this.acknowledgeSampleReceipt = acknowledgeSampleReceipt;
    }

    public String getAcknowledgeRecordReceipt() {
        return acknowledgeRecordReceipt;
    }

    public void setAcknowledgeRecordReceipt(String acknowledgeRecordReceipt) {
        this.acknowledgeRecordReceipt = acknowledgeRecordReceipt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
            "LaboratoryRequest [laboratoryRequestId=" +
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
            ", middlewareClientUuid=" +
            middlewareClientUuid +
            ", clientId=" +
            clientId +
            ", client=" +
            client +
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
            ", retry=" +
            retry +
            ", errorReason=" +
            errorReason +
            ", acknowledgeSampleReceipt=" +
            acknowledgeSampleReceipt +
            ", acknowledgeRecordReceipt=" +
            acknowledgeRecordReceipt +
            ", dateCollected=" +
            dateCollected +
            ", dateTested=" +
            dateTested +
            ", reviewState=" +
            reviewState +
            ", dispatched=" +
            dispatched +
            ", status=" +
            status +
            ", sentToLims=" +
            sentToLims +
            ", sentToEhr=" +
            sentToEhr +
            ", resultStatus=" +
            resultStatus +
            ", dateResultReceivedFromLims=" +
            dateResultReceivedFromLims +
            "]"
        );
    }
}
