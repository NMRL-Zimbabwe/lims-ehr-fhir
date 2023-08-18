package zw.org.nmr.limsehr.service.dto.patient.resolver;

public class PatientIdentifierDTO {

    private String number;

    private Type type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Identification [number=" + number + ", type=" + type + "]";
    }
}
