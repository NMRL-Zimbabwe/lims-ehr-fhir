package zw.org.nmr.limsehr.service.dto;

import java.time.LocalDate;
import java.util.Arrays;
import zw.org.nmr.limsehr.service.dto.patient.resolver.PatientArtResolverDTO;

public class PatientDTO {

    private String patientId;

    private String firstname;

    private String lastname;

    private LocalDate dob;

    private String gender;

    private PatientArtResolverDTO[] art;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PatientArtResolverDTO[] getArt() {
        return art;
    }

    public void setArt(PatientArtResolverDTO[] art) {
        this.art = art;
    }

    @Override
    public String toString() {
        return (
            "PatientDTO [patientId=" +
            patientId +
            ", firstname=" +
            firstname +
            ", lastname=" +
            lastname +
            ", dob=" +
            dob +
            ", gender=" +
            gender +
            ", art=" +
            Arrays.toString(art) +
            "]"
        );
    }
}
