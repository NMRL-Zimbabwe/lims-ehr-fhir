package zw.org.nmr.limsehr.service.dto.unified.lims_request;

public class UnifiedLimsRequest {

    private UnifiedLimsRequestAnalysisRequestDTO AnalysisRequest;

    private UnifiedLimsRequestBatchDTO Batch;

    private UnifiedLimsRequestPatientDTO Patient;

    private String labId;

    private String labName;

    public UnifiedLimsRequestAnalysisRequestDTO getAnalysisRequest() {
        return AnalysisRequest;
    }

    public void setAnalysisRequest(UnifiedLimsRequestAnalysisRequestDTO analysisRequest) {
        AnalysisRequest = analysisRequest;
    }

    public UnifiedLimsRequestBatchDTO getBatch() {
        return Batch;
    }

    public void setBatch(UnifiedLimsRequestBatchDTO batch) {
        Batch = batch;
    }

    public UnifiedLimsRequestPatientDTO getPatient() {
        return Patient;
    }

    public void setPatient(UnifiedLimsRequestPatientDTO patient) {
        Patient = patient;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    @Override
    public String toString() {
        return (
            "UnifiedLimsRequest [AnalysisRequest=" +
            AnalysisRequest +
            ", Batch=" +
            Batch +
            ", Patient=" +
            Patient +
            ", labId=" +
            labId +
            ", labName=" +
            labName +
            "]"
        );
    }
}
