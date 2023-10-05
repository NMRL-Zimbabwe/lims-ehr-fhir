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
@Table(name = "sample_type_mapper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SampleTypeMapper extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @JsonIgnore
    @Column(name = "sample_type_id")
    private String sampleTypeId;

    @Column(name = "ehr_sample_type_name")
    private String ehrSampleTypeName;

    @JsonIgnore
    @Column(name = "laboratory_id")
    private String laboratoryId;

    @JsonIgnore
    @Column(name = "laboratory_sample_type_name")
    private String laboratorySampleTypeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getEhrSampleTypeName() {
        return ehrSampleTypeName;
    }

    public void setEhrSampleTypeName(String ehrSampleTypeName) {
        this.ehrSampleTypeName = ehrSampleTypeName;
    }

    public String getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(String laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public String getLaboratorySampleTypeName() {
        return laboratorySampleTypeName;
    }

    public void setLaboratorySampleTypeName(String laboratorySampleTypeName) {
        this.laboratorySampleTypeName = laboratorySampleTypeName;
    }

    @Override
    public String toString() {
        return (
            "SampleTypeMapper{" +
            "id='" +
            id +
            '\'' +
            ", sampleTypeId='" +
            sampleTypeId +
            '\'' +
            ", ehrSampleTypeName='" +
            ehrSampleTypeName +
            '\'' +
            ", laboratoryId='" +
            laboratoryId +
            '\'' +
            ", laboratorySampleTypeName='" +
            laboratorySampleTypeName +
            '\'' +
            '}'
        );
    }
}
