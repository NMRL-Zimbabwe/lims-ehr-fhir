package zw.org.nmr.limsehr.service.dto.unified.lims_request;

import java.util.Arrays;

public class UnifiedLimsRequestAnalysisRequestDTO {

    private String DateSampled;

    private String[] Profiles;

    private String SampleType;

    private String Contact;

    private String Template;

    private String ClientSampleId;

    public String getDateSampled() {
        return DateSampled;
    }

    public void setDateSampled(String DateSampled) {
        this.DateSampled = DateSampled;
    }

    public String[] getProfiles() {
        return Profiles;
    }

    public void setProfiles(String[] Profiles) {
        this.Profiles = Profiles;
    }

    public String getSampleType() {
        return SampleType;
    }

    public void setSampleType(String SampleType) {
        this.SampleType = SampleType;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getTemplate() {
        return Template;
    }

    public void setTemplate(String Template) {
        this.Template = Template;
    }

    public String getClientSampleId() {
        return ClientSampleId;
    }

    public void setClientSampleId(String clientSampleId) {
        ClientSampleId = clientSampleId;
    }

    @Override
    public String toString() {
        return (
            "UnifiedLimsRequestAnalysisRequestDTO [DateSampled=" +
            DateSampled +
            ", Profiles=" +
            Arrays.toString(Profiles) +
            ", SampleType=" +
            SampleType +
            ", Contact=" +
            Contact +
            ", Template=" +
            Template +
            ", ClientSampleId=" +
            ClientSampleId +
            "]"
        );
    }
}
