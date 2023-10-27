package zw.org.nmr.limsehr.service.dto;

import zw.org.nmr.limsehr.domain.SampleType;
import zw.org.nmr.limsehr.domain.Test;

public class EhrToLImsDTO {

    private SampleType sampleType;

    private Test test;

    public EhrToLImsDTO() {
        // Empty constructor needed for Jackson.
    }

    public EhrToLImsDTO(SampleType sampleType, Test test) {
        this.sampleType = sampleType;
        this.test = test;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "EhrToLImsDTO{" + "sampleType=" + sampleType + ", test=" + test + '}';
    }
}
