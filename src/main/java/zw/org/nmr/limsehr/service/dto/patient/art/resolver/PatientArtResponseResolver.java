package zw.org.nmr.limsehr.service.dto.patient.art.resolver;

public class PatientArtResponseResolver {

    private ArtData data;

    public ArtData getData() {
        return data;
    }

    public void setData(ArtData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PatientArtResponseResolver [data=" + data + "]";
    }
}
