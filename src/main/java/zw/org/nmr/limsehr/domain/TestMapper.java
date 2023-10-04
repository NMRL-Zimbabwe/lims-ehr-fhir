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
@Table(name = "test_mapper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestMapper extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @JsonIgnore
    @Column(name = "test_id")
    private String testId;

    @JsonIgnore
    @Column(name = "sample_type_id")
    private String sampleTypeId;

    @Column(name = "ehr_test_name")
    private String ehrTestName;

    @JsonIgnore
    @Column(name = "laboratory_id")
    private String laboratoryId;

    @JsonIgnore
    @Column(name = "laboratory_test_name")
    private String laboratoryTestName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getEhrTestName() {
        return ehrTestName;
    }

    public void setEhrTestName(String ehrTestName) {
        this.ehrTestName = ehrTestName;
    }

    public String getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(String laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public String getLaboratoryTestName() {
        return laboratoryTestName;
    }

    public void setLaboratoryTestName(String laboratoryTestName) {
        this.laboratoryTestName = laboratoryTestName;
    }

    @Override
    public String toString() {
        return (
            "TestMapper{" +
            "id='" +
            id +
            '\'' +
            ", testId='" +
            testId +
            '\'' +
            ", sampleTypeId='" +
            sampleTypeId +
            '\'' +
            ", ehrTestName='" +
            ehrTestName +
            '\'' +
            ", laboratoryId='" +
            laboratoryId +
            '\'' +
            ", laboratoryTestName='" +
            laboratoryTestName +
            '\'' +
            '}'
        );
    }
}
