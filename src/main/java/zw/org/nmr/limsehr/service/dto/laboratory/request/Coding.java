package zw.org.nmr.limsehr.service.dto.laboratory.request;

public class Coding {

    private String code;

    private AgentContext agentContext;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AgentContext getAgentContext() {
        return agentContext;
    }

    public void setAgentContext(AgentContext agentContext) {
        this.agentContext = agentContext;
    }

    @Override
    public String toString() {
        return "Coding [code=" + code + ", agentContext=" + agentContext + "]";
    }
}
