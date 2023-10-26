package zw.org.nmr.limsehr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import zw.org.nmr.limsehr.domain.Laboratory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LaboratoryDTO {

    private String id;

    private String name;

    private String code;

    private String routingKey;

    private String ehrCode;

    public LaboratoryDTO() {
        // Empty constructor needed for Jackson.
    }

    public LaboratoryDTO(Laboratory lab) {
        this.id = lab.getId();
        this.code = lab.getCode();
        this.name = lab.getName();
        this.routingKey = lab.getRoutingKey();
        this.ehrCode = lab.getEhrCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEhrCode() {
        return ehrCode;
    }

    public void setEhrCode(String ehrCode) {
        this.ehrCode = ehrCode;
    }

    @Override
    public String toString() {
        return (
            "LaboratoryDTO [id=" + id + ", name=" + name + ", code=" + code + ", routingKey=" + routingKey + ", ehrCode=" + ehrCode + "]"
        );
    }
}
