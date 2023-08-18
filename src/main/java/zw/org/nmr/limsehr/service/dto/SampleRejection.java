package zw.org.nmr.limsehr.service.dto;

public class SampleRejection {

    private String date;

    private RejectSampleReason rejectSampleReason;

    private String labReference;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabReference() {
        return labReference;
    }

    public void setLabReference(String labReference) {
        this.labReference = labReference;
    }

    public RejectSampleReason getRejectSampleReason() {
        return rejectSampleReason;
    }

    public void setRejectSampleReason(RejectSampleReason rejectSampleReason) {
        this.rejectSampleReason = rejectSampleReason;
    }

    @Override
    public String toString() {
        return "RejectSample [date=" + date + ", rejectSampleReason=" + rejectSampleReason + ", labReference=" + labReference + "]";
    }
}
