package zw.org.nmr.limsehr.service.dto.patient.art.resolver;

import java.util.List;

public class Art {

    private List<PersonArt> person;

    public List<PersonArt> getPerson() {
        return person;
    }

    public void setPerson(List<PersonArt> person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Art [person=" + person + "]";
    }
}
