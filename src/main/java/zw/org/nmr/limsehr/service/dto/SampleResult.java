package zw.org.nmr.limsehr.service.dto;

public class SampleResult {

    private String coded;

    private String date;

    private String result;

    private String agentId;

    private String contextId;

    private String labReference;

    public String getCoded() {
        return coded;
    }

    public void setCoded(String coded) {
        this.coded = coded;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getLabReference() {
        return labReference;
    }

    public void setLabReference(String labReference) {
        this.labReference = labReference;
    }

    @Override
    public String toString() {
        return (
            "SampleResult [coded=" +
            coded +
            ", date=" +
            date +
            ", result=" +
            result +
            ", agentId=" +
            agentId +
            ", contextId=" +
            contextId +
            ", labReference=" +
            labReference +
            "]"
        );
    }
}
