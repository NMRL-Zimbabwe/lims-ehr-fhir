package zw.org.nmr.limsehr.service.dto.patient.resolver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.List;
import zw.org.nmr.limsehr.service.dto.Age;

public class PatientResolverDTO {

    private String firstname;

    private String birthdate;

    private PatientAddressResolverDTO address;

    private String sex;

    private Age age;

    public List<PatientIdentifierDTO> identification;

    @JsonIgnore
    private PatientArtResolverDTO[] art;

    @JsonIgnore
    public List<String> phone;

    @JsonIgnore
    private PatientReligionResolverDTO religion;

    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public PatientAddressResolverDTO getAddress() {
        return address;
    }

    public void setAddress(PatientAddressResolverDTO address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public PatientArtResolverDTO[] getArt() {
        return art;
    }

    public void setArt(PatientArtResolverDTO[] art) {
        this.art = art;
    }

    public PatientReligionResolverDTO getReligion() {
        return religion;
    }

    public void setReligion(PatientReligionResolverDTO religion) {
        this.religion = religion;
    }

    public List<PatientIdentifierDTO> getIdentification() {
        return identification;
    }

    public void setIdentification(List<PatientIdentifierDTO> identification) {
        this.identification = identification;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return (
            "PatientResolverDTO [firstname=" +
            firstname +
            ", birthdate=" +
            birthdate +
            ", address=" +
            address +
            ", sex=" +
            sex +
            ", age=" +
            age +
            ", identification=" +
            identification +
            ", art=" +
            Arrays.toString(art) +
            ", phone=" +
            phone +
            ", religion=" +
            religion +
            ", lastname=" +
            lastname +
            "]"
        );
    }
}
