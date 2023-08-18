package zw.org.nmr.limsehr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Table(name = "contigency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contigency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "client_sample_id", unique = true, nullable = false)
    private String clientSampleId;

    private String name;

    private String surname;

    private String gender;

    private String client;

    @Column(name = "client_patient_id")
    private String clientPatientId;

    private String dob;

    @Column(name = "dob_estimated")
    private String dobEstimated;

    @Column(name = "phoneNumber")
    private String phone_number;

    @Column(name = "consent_to_sms")
    private String consentToSms;

    @Column(name = "client_contact")
    private String clientContact;

    @Column(name = "analysis_profiles")
    private String analysisProfiles;

    @Column(name = "date_sampled")
    private String dateSampled;

    @Column(name = "sample_type")
    private String sampleType;

    @Column(name = "case_type")
    private String caseType;

    private String pregnant;

    @Column(name = "breast_feeding")
    private String breastFeeding;

    @Column(name = "reason_for_vl_test")
    private String reasonForVlTest;

    private String treatment;

    @Column(name = "treatment_start_date")
    private String treatmentStartDate;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "sent_to_lims")
    private String sentToLims;

    @Column(name = "received_in_lims")
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

    public String getDobEstimated() {
        return dobEstimated;
    }

    public void setDobEstimated(String dobEstimated) {
        this.dobEstimated = dobEstimated;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return (
            "Contigency [clientSampleId=" +
            clientSampleId +
            ", name=" +
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
            ", dobEstimated=" +
            dobEstimated +
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
