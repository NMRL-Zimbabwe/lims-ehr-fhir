package zw.org.nmr.limsehr.service.dto.laboratory.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DTOforLIMS {

    private String Firstname;

    private String ClientPatientId;

    private String PrimaryReferrer;

    private String parent_path;

    private String Gender;

    private String portal_type;

    private String Surname;

    private SampleDTOforLIMS sample;

    private String BirthDate;

    @JsonIgnore
    private String MobilePhone;

    @JsonIgnore
    private boolean ConsentSMS;

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getPrimaryReferrer() {
        return PrimaryReferrer;
    }

    public void setPrimaryReferrer(String PrimaryReferrer) {
        this.PrimaryReferrer = PrimaryReferrer;
    }

    public String getParent_path() {
        return parent_path;
    }

    public void setParent_path(String parent_path) {
        this.parent_path = parent_path;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getPortal_type() {
        return portal_type;
    }

    public void setPortal_type(String portal_type) {
        this.portal_type = portal_type;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public SampleDTOforLIMS getSample() {
        return sample;
    }

    public void setSample(SampleDTOforLIMS sample) {
        this.sample = sample;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public String getClientPatientId() {
        return ClientPatientId;
    }

    public void setClientPatientId(String clientPatientId) {
        ClientPatientId = clientPatientId;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public boolean isConsentSMS() {
        return ConsentSMS;
    }

    public void setConsentSMS(boolean consentSMS) {
        ConsentSMS = consentSMS;
    }

    @Override
    public String toString() {
        return (
            "DTOforLIMS [Firstname=" +
            Firstname +
            ", ClientPatientId=" +
            ClientPatientId +
            ", PrimaryReferrer=" +
            PrimaryReferrer +
            ", parent_path=" +
            parent_path +
            ", Gender=" +
            Gender +
            ", portal_type=" +
            portal_type +
            ", Surname=" +
            Surname +
            ", sample=" +
            sample +
            ", BirthDate=" +
            BirthDate +
            ", MobilePhone=" +
            MobilePhone +
            ", ConsentSMS=" +
            ConsentSMS +
            "]"
        );
    }
}
