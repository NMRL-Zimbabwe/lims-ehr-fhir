package zw.org.nmr.limsehr.service.dto.patient.resolver;

public class PatientMainResponseResolver {

    private PatientDataResolverDTO data;

    public PatientDataResolverDTO getData() {
        return data;
    }

    public void setData(PatientDataResolverDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [data=" + data + "]";
    }
}
