package zw.org.nmr.limsehr.service.dto;

import java.util.List;
import zw.org.nmr.limsehr.domain.*;

public class MapperDataDTO {

    private List<EhrSampleType> ehrSampleTypes;

    private List<EhrTest> ehrTests;

    private List<SampleType> limsSampleTypes;

    private List<Test> limsTests;

    private List<LaboratoryDTO> laboratories;

    public MapperDataDTO() {}

    public MapperDataDTO(
        List<EhrSampleType> ehrSampleTypes,
        List<EhrTest> ehrTests,
        List<SampleType> limsSampleTypes,
        List<Test> limsTests,
        List<LaboratoryDTO> laboratories
    ) {
        this.ehrSampleTypes = ehrSampleTypes;
        this.ehrTests = ehrTests;
        this.limsSampleTypes = limsSampleTypes;
        this.limsTests = limsTests;
        this.laboratories = laboratories;
    }

    public List<EhrSampleType> getEhrSampleTypes() {
        return ehrSampleTypes;
    }

    public void setEhrSampleTypes(List<EhrSampleType> ehrSampleTypes) {
        this.ehrSampleTypes = ehrSampleTypes;
    }

    public List<EhrTest> getEhrTests() {
        return ehrTests;
    }

    public void setEhrTests(List<EhrTest> ehrTests) {
        this.ehrTests = ehrTests;
    }

    public List<SampleType> getLimsSampleTypes() {
        return limsSampleTypes;
    }

    public void setLimsSampleTypes(List<SampleType> limsSampleTypes) {
        this.limsSampleTypes = limsSampleTypes;
    }

    public List<Test> getLimsTests() {
        return limsTests;
    }

    public void setLimsTests(List<Test> limsTests) {
        this.limsTests = limsTests;
    }

    public List<LaboratoryDTO> getLaboratories() {
        return laboratories;
    }

    public void setLaboratories(List<LaboratoryDTO> laboratories) {
        this.laboratories = laboratories;
    }

    @Override
    public String toString() {
        return (
            "MapperDataDTO{" +
            "ehrSampleTypes=" +
            ehrSampleTypes +
            ", ehrTests=" +
            ehrTests +
            ", limsSampleTypes=" +
            limsSampleTypes +
            ", limsTests=" +
            limsTests +
            ", laboratories=" +
            laboratories +
            '}'
        );
    }
}
