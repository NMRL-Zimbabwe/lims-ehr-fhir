package zw.org.nmr.limsehr.service.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AcknowledgementFromLims {

    private String clientPatientId;

    private String patientId;

    private String patientUID;

    private String sampleId;

    private String clientSampleId;

    private String sampleUID;

    private String result;

    private String unit;

    private String status;

    private String remarks;

    private String dateTested;

    private String dateReceived;

    private String datePublished;

    private String submitter;

    private String verifier;

    private String method;

    private String instrument;

    public AcknowledgementFromLims() {}

    public AcknowledgementFromLims(
        String clientPatientId,
        String patientId,
        String patientUID,
        String sampleId,
        String clientSampleId,
        String sampleUID,
        String result,
        String unit,
        String status,
        String remarks,
        String dateTested,
        String dateReceived,
        String datePublished,
        String submitter,
        String verifier,
        String method,
        String instrument
    ) {
        this.clientPatientId = clientPatientId;
        this.patientId = patientId;
        this.patientUID = patientUID;
        this.sampleId = sampleId;
        this.clientSampleId = clientSampleId;
        this.sampleUID = sampleUID;
        this.result = result;
        this.unit = unit;
        this.status = status;
        this.remarks = remarks;
        this.dateTested = dateTested;
        this.dateReceived = dateReceived;
        this.datePublished = datePublished;
        this.submitter = submitter;
        this.verifier = verifier;
        this.method = method;
        this.instrument = instrument;
    }

    public String getClientPatientId() {
        return clientPatientId;
    }

    public void setClientPatientId(String clientPatientId) {
        this.clientPatientId = clientPatientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientUID() {
        return patientUID;
    }

    public void setPatientUID(String patientUID) {
        this.patientUID = patientUID;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getClientSampleId() {
        return clientSampleId;
    }

    public void setClientSampleId(String clientSampleId) {
        this.clientSampleId = clientSampleId;
    }

    public String getSampleUID() {
        return sampleUID;
    }

    public void setSampleUID(String sampleUID) {
        this.sampleUID = sampleUID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDateTested() {
        return dateTested;
    }

    public void setDateTested(String dateTested) {
        this.dateTested = dateTested;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
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
            "AcknowledgementFromLims{" +
            "clientPatientId='" +
            clientPatientId +
            '\'' +
            ", patientId='" +
            patientId +
            '\'' +
            ", patientUID='" +
            patientUID +
            '\'' +
            ", sampleId='" +
            sampleId +
            '\'' +
            ", clientSampleId='" +
            clientSampleId +
            '\'' +
            ", sampleUID='" +
            sampleUID +
            '\'' +
            ", result='" +
            result +
            '\'' +
            ", unit='" +
            unit +
            '\'' +
            ", status='" +
            status +
            '\'' +
            ", remarks='" +
            remarks +
            '\'' +
            ", dateTested=" +
            dateTested +
            ", dateReceived=" +
            dateReceived +
            ", datePublished=" +
            datePublished +
            ", submitter='" +
            submitter +
            '\'' +
            ", verifier='" +
            verifier +
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
