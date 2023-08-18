package zw.org.nmr.limsehr.service.dto;

public class ContigencyDTO {

    private String name;

    private String surname;

    private String gender;

    private String client;

    private String clientPatientId;

    private String dob;

    private String dob_estimated;

    private String phone_number;

    private String consentToSms;

    private String clientContact;

    private String analysisProfiles;

    private String dateSampled;

    private String sampleType;

    private String clientSampleId;

    private String caseType;

    private String pregnant;

    private String breastFeeding;

    private String reasonForVlTest;

    private String treatment;

    private String treatmentStartDate;

    private String clientName;

    private String sentToLims;

    private String receivedInLims;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientPatientId() {
        return clientPatientId;
    }

    public void setClientPatientId(String clientPatientId) {
        this.clientPatientId = clientPatientId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob_estimated() {
        return dob_estimated;
    }

    public void setDob_estimated(String dob_estimated) {
        this.dob_estimated = dob_estimated;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getConsentToSms() {
        return consentToSms;
    }

    public void setConsentToSms(String consentToSms) {
        this.consentToSms = consentToSms;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getAnalysisProfiles() {
        return analysisProfiles;
    }

    public void setAnalysisProfiles(String analysisProfiles) {
        this.analysisProfiles = analysisProfiles;
    }

    public String getDateSampled() {
        return dateSampled;
    }

    public void setDateSampled(String dateSampled) {
        this.dateSampled = dateSampled;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getClientSampleId() {
        return clientSampleId;
    }

    public void setClientSampleId(String clientSampleId) {
        this.clientSampleId = clientSampleId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public String getBreastFeeding() {
        return breastFeeding;
    }

    public void setBreastFeeding(String breastFeeding) {
        this.breastFeeding = breastFeeding;
    }

    public String getReasonForVlTest() {
        return reasonForVlTest;
    }

    public void setReasonForVlTest(String reasonForVlTest) {
        this.reasonForVlTest = reasonForVlTest;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getTreatmentStartDate() {
        return treatmentStartDate;
    }

    public void setTreatmentStartDate(String treatmentStartDate) {
        this.treatmentStartDate = treatmentStartDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSentToLims() {
        return sentToLims;
    }

    public void setSentToLims(String sentToLims) {
        this.sentToLims = sentToLims;
    }

    public String getReceivedInLims() {
        return receivedInLims;
    }

    public void setReceivedInLims(String receivedInLims) {
        this.receivedInLims = receivedInLims;
    }

    @Override
    public String toString() {
        return (
            "ContigencyDTO [name=" +
            name +
            ", surname=" +
            surname +
            ", gender=" +
            gender +
            ", client=" +
            client +
            ", clientPatientId=" +
            clientPatientId +
            ", dob=" +
            dob +
            ", dob_estimated=" +
            dob_estimated +
            ", phone_number=" +
            phone_number +
            ", consentToSms=" +
            consentToSms +
            ", clientContact=" +
            clientContact +
            ", analysisProfiles=" +
            analysisProfiles +
            ", dateSampled=" +
            dateSampled +
            ", sampleType=" +
            sampleType +
            ", clientSampleId=" +
            clientSampleId +
            ", caseType=" +
            caseType +
            ", pregnant=" +
            pregnant +
            ", breastFeeding=" +
            breastFeeding +
            ", reasonForVlTest=" +
            reasonForVlTest +
            ", treatment=" +
            treatment +
            ", treatmentStartDate=" +
            treatmentStartDate +
            ", clientName=" +
            clientName +
            ", sentToLims=" +
            sentToLims +
            ", receivedInLims=" +
            receivedInLims +
            "]"
        );
    }
}
