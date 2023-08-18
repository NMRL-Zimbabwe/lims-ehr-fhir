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
@Table(name = "patient_phone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PatientPhone extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "patient_phone_id", unique = true, nullable = false)
    private String patientPhoneId;

    private String number;

    @Column(name = "patient_id")
    private String patientId;

    public String getPatientPhoneId() {
        return patientPhoneId;
    }

    public void setPatientPhoneId(String patientPhoneId) {
        this.patientPhoneId = patientPhoneId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientPhone)) {
            return false;
        }
        return patientPhoneId != null && patientPhoneId.equals(((PatientPhone) o).patientPhoneId);
    }

    @Override
    public String toString() {
        return "PatientPhone [patientPhoneId=" + patientPhoneId + ", number=" + number + ", patientId=" + patientId + "]";
    }
}
