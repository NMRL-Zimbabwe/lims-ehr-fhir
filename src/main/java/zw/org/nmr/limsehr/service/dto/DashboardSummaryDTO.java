package zw.org.nmr.limsehr.service.dto;

public class DashboardSummaryDTO {

    long patients;

    long totalSamples;

    long totalSamplesAcknowledged;

    public long getPatients() {
        return patients;
    }

    public void setPatients(long patients) {
        this.patients = patients;
    }

    public long getTotalSamples() {
        return totalSamples;
    }

    public void setTotalSamples(long totalSamples) {
        this.totalSamples = totalSamples;
    }

    public long getTotalSamplesAcknowledged() {
        return totalSamplesAcknowledged;
    }

    public void setTotalSamplesAcknowledged(long totalSamplesAcknowledged) {
        this.totalSamplesAcknowledged = totalSamplesAcknowledged;
    }

    @Override
    public String toString() {
        return (
            "DashboardSummaryDTO [patients=" +
            patients +
            ", totalSamples=" +
            totalSamples +
            ", totalSamplesAcknowledged=" +
            totalSamplesAcknowledged +
            "]"
        );
    }
}
