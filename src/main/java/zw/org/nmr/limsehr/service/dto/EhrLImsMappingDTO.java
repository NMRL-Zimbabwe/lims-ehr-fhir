package zw.org.nmr.limsehr.service.dto;

import zw.org.nmr.limsehr.domain.*;

public class EhrLImsMappingDTO {

    private String id;

    private String ehrSampleTypeId;

    private EhrSampleType ehrSampleType;

    private String ehrTestId;

    private EhrTest ehrTest;
    private String limsSampleTypeId;

    private SampleType limsSampleType;

    private String limsTestId;

    private Test limsTest;

    private String laboratoryId;

    private Laboratory laboratory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EhrLImsMappingDTO() {}

    public EhrLImsMappingDTO(
        String id,
        String ehrSampleTypeId,
        EhrSampleType ehrSampleType,
        String ehrTestId,
        EhrTest ehrTest,
        String limsSampleTypeId,
        SampleType limsSampleType,
        String limsTestId,
        Test limsTest,
        String laboratoryId,
        Laboratory laboratory
    ) {
        this.id = id;
        this.ehrSampleTypeId = ehrSampleTypeId;
        this.ehrSampleType = ehrSampleType;
        this.ehrTestId = ehrTestId;
        this.ehrTest = ehrTest;
        this.limsSampleTypeId = limsSampleTypeId;
        this.limsSampleType = limsSampleType;
        this.limsTestId = limsTestId;
        this.limsTest = limsTest;
        this.laboratoryId = laboratoryId;
        this.laboratory = laboratory;
    }

    public String getEhrSampleTypeId() {
        return ehrSampleTypeId;
    }

    public void setEhrSampleTypeId(String ehrSampleTypeId) {
        this.ehrSampleTypeId = ehrSampleTypeId;
    }

    public EhrSampleType getEhrSampleType() {
        return ehrSampleType;
    }

    public void setEhrSampleType(EhrSampleType ehrSampleType) {
        this.ehrSampleType = ehrSampleType;
    }

    public String getEhrTestId() {
        return ehrTestId;
    }

    public void setEhrTestId(String ehrTestId) {
        this.ehrTestId = ehrTestId;
    }

    public EhrTest getEhrTest() {
        return ehrTest;
    }

    public void setEhrTest(EhrTest ehrTest) {
        this.ehrTest = ehrTest;
    }

    public String getLimsSampleTypeId() {
        return limsSampleTypeId;
    }

    public void setLimsSampleTypeId(String limsSampleTypeId) {
        this.limsSampleTypeId = limsSampleTypeId;
    }

    public SampleType getLimsSampleType() {
        return limsSampleType;
    }

    public void setLimsSampleType(SampleType limsSampleType) {
        this.limsSampleType = limsSampleType;
    }

    public String getLimsTestId() {
        return limsTestId;
    }

    public void setLimsTestId(String limsTestId) {
        this.limsTestId = limsTestId;
    }

    public Test getLimsTest() {
        return limsTest;
    }

    public void setLimsTest(Test limsTest) {
        this.limsTest = limsTest;
    }

    public String getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(String laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    @Override
    public String toString() {
        return (
            "EhrLImsMappingDTO{" +
            "id='" +
            id +
            '\'' +
            ", ehrSampleTypeId='" +
            ehrSampleTypeId +
            '\'' +
            ", ehrSampleType=" +
            ehrSampleType +
            ", ehrTestId='" +
            ehrTestId +
            '\'' +
            ", ehrTest=" +
            ehrTest +
            ", limsSampleTypeId='" +
            limsSampleTypeId +
            '\'' +
            ", limsSampleType=" +
            limsSampleType +
            ", limsTestId='" +
            limsTestId +
            '\'' +
            ", limsTest=" +
            limsTest +
            ", laboratoryId='" +
            laboratoryId +
            '\'' +
            ", laboratory=" +
            laboratory +
            '}'
        );
    }
}
