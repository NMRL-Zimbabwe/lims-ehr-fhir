package zw.org.nmr.limsehr.service.dto.laboratory.request;

public class SampleDTOforLIMS {

    private String DateSampled;

    private String Profiles;

    private String Patient;

    private String parent_path;

    private String portal_type;

    private String SampleType;

    private String clientSampleId;

    public String getDateSampled() {
        return DateSampled;
    }

    public void setDateSampled(String DateSampled) {
        this.DateSampled = DateSampled;
    }

    public String getProfiles() {
        return Profiles;
    }

    public void setProfiles(String Profiles) {
        this.Profiles = Profiles;
    }

    public String getPatient() {
        return Patient;
    }

    public void setPatient(String Patient) {
        this.Patient = Patient;
    }

    public String getParent_path() {
        return parent_path;
    }

    public void setParent_path(String parent_path) {
        this.parent_path = parent_path;
    }

    public String getPortal_type() {
        return portal_type;
    }

    public void setPortal_type(String portal_type) {
        this.portal_type = portal_type;
    }

    public String getSampleType() {
        return SampleType;
    }

    public void setSampleType(String SampleType) {
        this.SampleType = SampleType;
    }

    public String getClientSampleId() {
        return clientSampleId;
    }

    public void setClientSampleId(String clientSampleId) {
        this.clientSampleId = clientSampleId;
    }

    @Override
    public String toString() {
        return (
            "SampleDTOforLIMS [DateSampled=" +
            DateSampled +
            ", Profiles=" +
            Profiles +
            ", Patient=" +
            Patient +
            ", parent_path=" +
            parent_path +
            ", portal_type=" +
            portal_type +
            ", SampleType=" +
            SampleType +
            ", clientSampleId=" +
            clientSampleId +
            "]"
        );
    }
}
