package zw.org.nmr.limsehr.service.dto;

public class RejectionReason {

    private String agentId;

    private String code;

    private String contextId;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    @Override
    public String toString() {
        return "Reason [agentId=" + agentId + ", code=" + code + ", contextId=" + contextId + "]";
    }
}
