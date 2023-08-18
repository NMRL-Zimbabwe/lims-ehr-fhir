package zw.org.nmr.limsehr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import zw.org.nmr.limsehr.service.dto.patient.resolver.PatientResolverDTO;

public class PatientGraphqlDTO {

    private Map<String, String> data;

    @JsonIgnore
    Map<String, PatientResolverDTO> person;

    public Map<String, PatientResolverDTO> getPerson() {
        return person;
    }

    public void setPerson(Map<String, PatientResolverDTO> person) {
        this.person = person;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
