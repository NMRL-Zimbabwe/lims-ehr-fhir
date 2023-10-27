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
@Table(name = "lims_ehr_mapper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LimsEhrMapper extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "ehr_sample_type_id")
    private String ehrSampleTypeId;

    @Column(name = "ehr_test_id")
    private String ehrTestId;

    @Column(name = "lims_sample_type_id")
    private String limsSampleTypeId;

    @Column(name = "lims_test_id")
    private String limsTestId;

    @Column(name = "laboratory_id")
    private String laboratoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEhrSampleTypeId() {
        return ehrSampleTypeId;
    }

    public void setEhrSampleTypeId(String ehrSampleTypeId) {
        this.ehrSampleTypeId = ehrSampleTypeId;
    }

    public String getEhrTestId() {
        return ehrTestId;
    }

    public void setEhrTestId(String ehrTestId) {
        this.ehrTestId = ehrTestId;
    }

    public String getLimsSampleTypeId() {
        return limsSampleTypeId;
    }

    public void setLimsSampleTypeId(String limsSampleTypeId) {
        this.limsSampleTypeId = limsSampleTypeId;
    }

    public String getLimsTestId() {
        return limsTestId;
    }

    public void setLimsTestId(String limsTestId) {
        this.limsTestId = limsTestId;
    }

    public String getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(String laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    @Override
    public String toString() {
        return (
            "LimsEhrMapper{" +
            "id='" +
            id +
            '\'' +
            ", ehrSampleTypeId='" +
            ehrSampleTypeId +
            '\'' +
            ", ehrTestId='" +
            ehrTestId +
            '\'' +
            ", limsSampleTypeId='" +
            limsSampleTypeId +
            '\'' +
            ", limsTestId='" +
            limsTestId +
            '\'' +
            ", laboratoryId='" +
            laboratoryId +
            '\'' +
            '}'
        );
    }
}
