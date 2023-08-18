package zw.org.nmr.limsehr.service.dto;

public class SampleAcceptance {

    private String date;

    private RejectionReason reason;

    private String labReference;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RejectionReason getReason() {
        return reason;
    }

    public void setReason(RejectionReason reason) {
        this.reason = reason;
    }

    public String getLabReference() {
        return labReference;
    }

    public void setLabReference(String labReference) {
        this.labReference = labReference;
    }

    @Override
    public String toString() {
        return "SampleAcceptance [date=" + date + ", reason=" + reason + ", labReference=" + labReference + "]";
    }
}
