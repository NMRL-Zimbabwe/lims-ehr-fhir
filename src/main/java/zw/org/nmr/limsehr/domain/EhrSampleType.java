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
@Table(name = "ehr_sample_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EhrSampleType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "ehr_code")
    private String ehrCode;

    @Column(name = "loinc_code")
    private String loincCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEhrCode() {
        return ehrCode;
    }

    public void setEhrCode(String ehrCode) {
        this.ehrCode = ehrCode;
    }

    public String getLoincCode() {
        return loincCode;
    }

    public void setLoincCode(String loincCode) {
        this.loincCode = loincCode;
    }

    @Override
    public String toString() {
        return (
            "EhrSampleType{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", ehrCode='" +
            ehrCode +
            '\'' +
            ", loincCode='" +
            loincCode +
            '\'' +
            '}'
        );
    }
}
