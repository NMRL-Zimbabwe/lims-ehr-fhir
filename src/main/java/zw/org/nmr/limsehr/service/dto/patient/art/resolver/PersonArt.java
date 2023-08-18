package zw.org.nmr.limsehr.service.dto.patient.art.resolver;

public class PersonArt {

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PersonArt [number=" + number + "]";
    }
}
