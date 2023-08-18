package zw.org.nmr.limsehr.service.dto;

public class MiddlewareAnalysisRequestDTO {

    private String notificationId;
    private String reference;
    private String labReference = null;
    private String date;
    private String facilityId;
    private String patientId;
    private String sampleId;
    private String testId;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLabReference() {
        return labReference;
    }

    public void setLabReference(String labReference) {
        this.labReference = labReference;
    }

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

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    @Override
    public String toString() {
        return (
            "MiddlewareAnalysisRequestDTO [notificationId=" +
            notificationId +
            ", reference=" +
            reference +
            ", labReference=" +
            labReference +
            ", date=" +
            date +
            ", facilityId=" +
            facilityId +
            ", patientId=" +
            patientId +
            ", sampleId=" +
            sampleId +
            ", testId=" +
            testId +
            "]"
        );
    }
}
