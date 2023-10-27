package zw.org.nmr.limsehr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private String labId;

    @Column(name = "lab_name")
    private String labName;

    @Column(name = "client_sample_id")
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
    private String sampleId;

    @Column(name = "test_id")
    @JsonIgnore
    private String testId;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "sample_type_id")
    private String sampleTypeId;

    @Column(name = "sample_type_name")
    private String sampleTypeName;

    @Column(name = "lab_reference_sample_id")
    private String labReferenceSampleId;

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
    private LocalDateTime dateCollected;

    @Column(name = "date_tested")
    private LocalDateTime dateTested;

    @Column(name = "date_published")
    private LocalDateTime datePublished;

    @Column(name = "review_state")
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
    private LocalDateTime dateResultReceivedFromLims;

    @Column(name = "reason_for_test")
    private String reasonForTest;

    private boolean breastfeeding;

    private boolean pregnant;

    private String submitter;

    private String reviewer;

    private String method;

    private String instrument;

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

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
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

    public LocalDateTime getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(LocalDateTime dateCollected) {
        this.dateCollected = dateCollected;
    }

    public LocalDateTime getDateTested() {
        return dateTested;
    }

    public void setDateTested(LocalDateTime dateTested) {
        this.dateTested = dateTested;
    }

    public LocalDateTime getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDateTime datePublished) {
        this.datePublished = datePublished;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public LocalDateTime getDateResultReceivedFromLims() {
        return dateResultReceivedFromLims;
    }

    public void setDateResultReceivedFromLims(LocalDateTime dateResultReceivedFromLims) {
        this.dateResultReceivedFromLims = dateResultReceivedFromLims;
    }

    public String getReasonForTest() {
        return reasonForTest;
    }

    public void setReasonForTest(String reasonForTest) {
        this.reasonForTest = reasonForTest;
    }

    public boolean isBreastfeeding() {
        return breastfeeding;
    }

    public void setBreastfeeding(boolean breastfeeding) {
        this.breastfeeding = breastfeeding;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    @Override
    public String toString() {
        return (
            "LaboratoryRequest{" +
            "laboratoryRequestId='" +
            laboratoryRequestId +
            '\'' +
            ", limsAnalysisRequestUuid='" +
            limsAnalysisRequestUuid +
            '\'' +
            ", middlewareAnalysisRequestUuid='" +
            middlewareAnalysisRequestUuid +
            '\'' +
            ", labId='" +
            labId +
            '\'' +
            ", labName='" +
            labName +
            '\'' +
            ", clientSampleId='" +
            clientSampleId +
            '\'' +
            ", patient=" +
            patient +
            ", middlewareClientUuid='" +
            middlewareClientUuid +
            '\'' +
            ", clientId='" +
            clientId +
            '\'' +
            ", client='" +
            client +
            '\'' +
            ", sampleId='" +
            sampleId +
            '\'' +
            ", testId='" +
            testId +
            '\'' +
            ", testName='" +
            testName +
            '\'' +
            ", sampleTypeId='" +
            sampleTypeId +
            '\'' +
            ", sampleTypeName='" +
            sampleTypeName +
            '\'' +
            ", labReferenceSampleId='" +
            labReferenceSampleId +
            '\'' +
            ", result='" +
            result +
            '\'' +
            ", unit='" +
            unit +
            '\'' +
            ", remarks='" +
            remarks +
            '\'' +
            ", retry=" +
            retry +
            ", errorReason='" +
            errorReason +
            '\'' +
            ", acknowledgeSampleReceipt='" +
            acknowledgeSampleReceipt +
            '\'' +
            ", acknowledgeRecordReceipt='" +
            acknowledgeRecordReceipt +
            '\'' +
            ", dateCollected=" +
            dateCollected +
            ", dateTested=" +
            dateTested +
            ", datePublished=" +
            datePublished +
            ", reviewState='" +
            reviewState +
            '\'' +
            ", dispatched='" +
            dispatched +
            '\'' +
            ", status='" +
            status +
            '\'' +
            ", sentToLims='" +
            sentToLims +
            '\'' +
            ", sentToEhr='" +
            sentToEhr +
            '\'' +
            ", resultStatus='" +
            resultStatus +
            '\'' +
            ", dateResultReceivedFromLims=" +
            dateResultReceivedFromLims +
            ", reasonForTest='" +
            reasonForTest +
            '\'' +
            ", breastfeeding=" +
            breastfeeding +
            ", pregnant=" +
            pregnant +
            ", submitter='" +
            submitter +
            '\'' +
            ", reviewer='" +
            reviewer +
            '\'' +
            ", method='" +
            method +
            '\'' +
            ", instrument='" +
            instrument +
            '\'' +
            '}'
        );
    }
}
