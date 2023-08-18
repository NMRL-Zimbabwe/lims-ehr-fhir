package zw.org.nmr.limsehr.service.dto.laboratory.request;

public class LaboratoryRequestDataDTO {

    private LaboratoryRequestDataDetailDTO data;

    public LaboratoryRequestDataDetailDTO getData() {
        return data;
    }

    public void setData(LaboratoryRequestDataDetailDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LaoboratoryRequestDataDTO [data=" + data + "]";
    }
}
