package zw.org.nmr.limsehr.service.dto.laboratory.request;

public class Notification {

    private LaboratoryRequestEhrDTO id;

    public LaboratoryRequestEhrDTO getId() {
        return id;
    }

    public void setId(LaboratoryRequestEhrDTO id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Notification [id=" + id + "]";
    }
}
