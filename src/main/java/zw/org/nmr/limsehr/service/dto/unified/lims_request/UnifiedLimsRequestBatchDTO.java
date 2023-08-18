package zw.org.nmr.limsehr.service.dto.unified.lims_request;

public class UnifiedLimsRequestBatchDTO {

    private boolean VLBreastFeeding;

    private String CaseType;

    private String ReasonForVLtest;

    private String ClientCaseID;

    private boolean VLPregnant;

    public String getCaseType() {
        return CaseType;
    }

    public void setCaseType(String CaseType) {
        this.CaseType = CaseType;
    }

    public String getReasonForVLtest() {
        return ReasonForVLtest;
    }

    public void setReasonForVLtest(String ReasonForVLtest) {
        this.ReasonForVLtest = ReasonForVLtest;
    }

    public String getClientCaseID() {
        return ClientCaseID;
    }

    public void setClientCaseID(String ClientCaseID) {
        this.ClientCaseID = ClientCaseID;
    }

    public boolean isVLBreastFeeding() {
        return VLBreastFeeding;
    }

    public void setVLBreastFeeding(boolean vLBreastFeeding) {
        VLBreastFeeding = vLBreastFeeding;
    }

    public boolean isVLPregnant() {
        return VLPregnant;
    }

    public void setVLPregnant(boolean vLPregnant) {
        VLPregnant = vLPregnant;
    }

    @Override
    public String toString() {
        return (
            "UnifiedLimsRequestBatchDTO [VLBreastFeeding=" +
            VLBreastFeeding +
            ", CaseType=" +
            CaseType +
            ", ReasonForVLtest=" +
            ReasonForVLtest +
            ", ClientCaseID=" +
            ClientCaseID +
            ", VLPregnant=" +
            VLPregnant +
            "]"
        );
    }
}
