package zw.org.nmr.limsehr.service.dto.patient.resolver;

/**
 * A DTO representing a Patient Address This DTO is responsible for handling
 * queries from integration layer
 */

public class PatientAddressResolverDTO {

    private PatientTownResolverDTO town;

    private String city;

    private String street;

    public PatientTownResolverDTO getTown() {
        return town;
    }

    public void setTown(PatientTownResolverDTO town) {
        this.town = town;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address [town=" + town + ", city=" + city + ", street=" + street + "]";
    }
}
