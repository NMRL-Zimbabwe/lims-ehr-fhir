package zw.org.nmr.limsehr.service.dto.laboratory.request;

public class LaboratoryRequestDataDetailDTO {

    private Notification notification;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "LaboratoryRequestDataDetailDTO [notification=" + notification + "]";
    }
}
