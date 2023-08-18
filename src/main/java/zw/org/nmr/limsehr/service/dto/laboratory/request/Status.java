package zw.org.nmr.limsehr.service.dto.laboratory.request;

public class Status {

    private String date;

    private String status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Status [date=" + date + ", status=" + status + "]";
    }
}
