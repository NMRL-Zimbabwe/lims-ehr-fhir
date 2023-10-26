package zw.org.nmr.limsehr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReasonForTestDTO {

    private String id;

    private String reasonForTestId;

    private String name;

    @JsonIgnore
    private String state;

    @JsonIgnore
    private String loincCode;

    @JsonIgnore
    private String ehrCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReasonForTestId() {
        return reasonForTestId;
    }

    public void setReasonForTestId(String reasonForTestId) {
        this.reasonForTestId = reasonForTestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLoincCode() {
        return loincCode;
    }

    public void setLoincCode(String loincCode) {
        this.loincCode = loincCode;
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
            "ReasonForVlTest [id=" +
            id +
            ", reasonForTestId=" +
            reasonForTestId +
            ", name=" +
            name +
            ", state=" +
            state +
            ", loincCode=" +
            loincCode +
            ", ehrCode=" +
            ehrCode +
            "]"
        );
    }
}
