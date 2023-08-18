package zw.org.nmr.limsehr.service.dto.laboratory.request;

public class AgentContext {

    private Agent agent;

    private Context context;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AgentContext [agent=" + agent + ", context=" + context + "]";
    }
}
