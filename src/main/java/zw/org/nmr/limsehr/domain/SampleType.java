package zw.org.nmr.limsehr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "sample_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SampleType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "sample_type_id", unique = true, nullable = false)
    private String sampleTypeId;

    private String name;

    @JsonIgnore
    private String state;

    @JsonIgnore
    @Column(name = "loinc_code")
    private String loincCode;

    @JsonIgnore
    @Column(name = "ehr_code")
    private String ehrCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLoincCode() {
        return loincCode;
    }

    public void setLoincCode(String loincCode) {
        this.loincCode = loincCode;
    }

    public String getEhrCode() {
        return ehrCode;
    }

    public void setEhrCode(String ehrCode) {
        this.ehrCode = ehrCode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return (
            "SampleType{" +
            "id='" +
            id +
            '\'' +
            ", sampleTypeId='" +
            sampleTypeId +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", state='" +
            state +
            '\'' +
            ", loincCode='" +
            loincCode +
            '\'' +
            ", ehrCode='" +
            ehrCode +
            '\'' +
            '}'
        );
    }
}
