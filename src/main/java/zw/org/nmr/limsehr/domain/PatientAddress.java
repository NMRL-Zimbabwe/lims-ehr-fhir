package zw.org.nmr.limsehr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Patient Phone.
 */
@Entity
@Table(name = "patient_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PatientAddress extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "patient_address_id", unique = true, nullable = false)
    private String patientAddressId;

    private String city;

    @Column(name = "town_id")
    private String townId;

    @Column(name = "town_name")
    private String townName;

    private String street;

    @Column(name = "patient_id")
    private String patientId;

    public String getPatientAddressId() {
        return patientAddressId;
    }

    public void setPatientAddressId(String patientAddressId) {
        this.patientAddressId = patientAddressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return (
            "PatientAddress [patientAddressId=" +
            patientAddressId +
            ", city=" +
            city +
            ", townId=" +
            townId +
            ", townName=" +
            townName +
            ", street=" +
            street +
            ", patientId=" +
            patientId +
            "]"
        );
    }
}
