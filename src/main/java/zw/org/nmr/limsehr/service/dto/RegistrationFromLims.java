package zw.org.nmr.limsehr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

public class RegistrationFromLims {

    @JsonIgnore
    private String clientPatientId;

    @JsonIgnore
    private String patientId;

    @JsonIgnore
    private String patientUID;

    @JsonIgnore
    private String sampleId;

    @JsonIgnore
    private String clientSampleId;

    @JsonIgnore
    private String sampleUID;

    @JsonIgnore
    private String result;

    @JsonIgnore
    private String unit;

    @JsonIgnore
    private String status;

    @JsonIgnore
    private String remarks;

    @JsonIgnore
    private LocalDate dateTested;

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

    public LocalDate getDateTested() {
        return dateTested;
    }

    public void setDateTested(LocalDate dateTested) {
        this.dateTested = dateTested;
    }

    @Override
    public String toString() {
        return (
            "RegistrationFromLims [clientPatientId=" +
            clientPatientId +
            ", patientId=" +
            patientId +
            ", patientUID=" +
            patientUID +
            ", sampleId=" +
            sampleId +
            ", clientSampleId=" +
            clientSampleId +
            ", sampleUID=" +
            sampleUID +
            ", result=" +
            result +
            ", unit=" +
            unit +
            ", status=" +
            status +
            ", remarks=" +
            remarks +
            ", dateTested=" +
            dateTested +
            "]"
        );
    }
}
