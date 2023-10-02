package zw.org.nmr.limsehr.service.dto;

public class IDestinationLab {

    private String title;
    private String sampleType;

    public IDestinationLab() {}

    public IDestinationLab(String title, String sampleType) {
        this.title = title;
        this.sampleType = sampleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    @Override
    public String toString() {
        return "IDestinationLab{" + "title='" + title + '\'' + ", sampleType='" + sampleType + '\'' + '}';
    }
}
