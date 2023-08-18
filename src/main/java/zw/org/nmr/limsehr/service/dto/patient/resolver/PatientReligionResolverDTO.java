package zw.org.nmr.limsehr.service.dto.patient.resolver;

/**
 * A DTO representing a Patient Religion.
 * This DTO is responsible for handling
 * queries from integration layer
 */
public class PatientReligionResolverDTO {

    private String name;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Religion [name=" + name + ", id=" + id + "]";
    }
}
