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
@Table(name = "patient_identifier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PatientIdentifier extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "patient_identifier_id", unique = true, nullable = false)
    private String patientIdentifierId;

    private String number;

    private String type;

    @Column(name = "type_id")
    private String typeId;

    @Column(name = "patient_id")
    private String patientId;

    public String getPatientIdentifierId() {
        return patientIdentifierId;
    }

    public void setPatientIdentifierId(String patientIdentifierId) {
        this.patientIdentifierId = patientIdentifierId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientIdentifier)) {
            return false;
        }
        return patientIdentifierId != null && patientIdentifierId.equals(((PatientIdentifier) o).patientIdentifierId);
    }

    @Override
    public String toString() {
        return (
            "PatientIdentifier [patientIdentifierId=" +
            patientIdentifierId +
            ", number=" +
            number +
            ", type=" +
            type +
            ", typeId=" +
            typeId +
            ", patientId=" +
            patientId +
            "]"
        );
    }
}
