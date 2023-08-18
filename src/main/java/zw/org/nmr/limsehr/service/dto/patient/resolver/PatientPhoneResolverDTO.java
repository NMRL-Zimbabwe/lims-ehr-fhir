package zw.org.nmr.limsehr.service.dto.patient.resolver;

/**
 * A DTO representing a Patient Phones. This DTO is responsible for handling
 * queries from integration layer
 */
public class PatientPhoneResolverDTO {

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone [number=" + number + "]";
    }
}
