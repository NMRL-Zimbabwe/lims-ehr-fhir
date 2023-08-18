package zw.org.nmr.limsehr.service.dto;

public class Content {

    private String date;

    private String facilityId;

    private String patientId;

    private String sampleId;

    private String laboratoryId;

    private String clientReference;

    private String labReference;

    private String testId;

    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(String laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getLabReference() {
        return labReference;
    }

    public void setLabReference(String labReference) {
        this.labReference = labReference;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return (
            "Content [date=" +
            date +
            ", facilityId=" +
            facilityId +
            ", patientId=" +
            patientId +
            ", sampleId=" +
            sampleId +
            ", laboratoryId=" +
            laboratoryId +
            ", clientReference=" +
            clientReference +
            ", labReference=" +
            labReference +
            ", testId=" +
            testId +
            ", id=" +
            id +
            "]"
        );
    }
}
