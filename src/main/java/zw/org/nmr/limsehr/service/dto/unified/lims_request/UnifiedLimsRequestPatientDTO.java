package zw.org.nmr.limsehr.service.dto.unified.lims_request;

import java.util.Arrays;

public class UnifiedLimsRequestPatientDTO {

    private String Firstname;

    private String ClientPatientID;

    private UnifiedLimsRequestPatientIdentifiersDTO[] PatientIdentifiers;

    private String ClientID;

    private UnifiedLimsRequestCountryState CountryState;

    private String Gender;

    private String Surname;

    private boolean BirthDateEstimated;

    private String MobilePhone;

    private String BirthDate;

    private boolean ConsentSMS;

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getClientPatientID() {
        return ClientPatientID;
    }

    public void setClientPatientID(String clientPatientID) {
        ClientPatientID = clientPatientID;
    }

    public UnifiedLimsRequestPatientIdentifiersDTO[] getPatientIdentifiers() {
        return PatientIdentifiers;
    }

    public void setPatientIdentifiers(UnifiedLimsRequestPatientIdentifiersDTO[] patientIdentifiers) {
        PatientIdentifiers = patientIdentifiers;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public UnifiedLimsRequestCountryState getCountryState() {
        return CountryState;
    }

    public void setCountryState(UnifiedLimsRequestCountryState countryState) {
        CountryState = countryState;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public boolean isBirthDateEstimated() {
        return BirthDateEstimated;
    }

    public void setBirthDateEstimated(boolean birthDateEstimated) {
        BirthDateEstimated = birthDateEstimated;
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
            "UnifiedLimsRequestPatientDTO [Firstname=" +
            Firstname +
            ", ClientPatientID=" +
            ClientPatientID +
            ", PatientIdentifiers=" +
            Arrays.toString(PatientIdentifiers) +
            ", ClientID=" +
            ClientID +
            ", CountryState=" +
            CountryState +
            ", Gender=" +
            Gender +
            ", Surname=" +
            Surname +
            ", BirthDateEstimated=" +
            BirthDateEstimated +
            ", MobilePhone=" +
            MobilePhone +
            ", BirthDate=" +
            BirthDate +
            ", ConsentSMS=" +
            ConsentSMS +
            "]"
        );
    }
}
