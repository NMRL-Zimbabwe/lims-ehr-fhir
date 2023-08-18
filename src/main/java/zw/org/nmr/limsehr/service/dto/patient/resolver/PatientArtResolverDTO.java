package zw.org.nmr.limsehr.service.dto.patient.resolver;

/**
 * A DTO representing a Patient ART. This DTO is responsible for handling
 * queries from integration layer
 */

public class PatientArtResolverDTO {

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ArtDTO [number=" + number + "]";
    }
}
