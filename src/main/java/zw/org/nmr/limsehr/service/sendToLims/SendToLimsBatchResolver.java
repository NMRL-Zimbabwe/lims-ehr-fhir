package zw.org.nmr.limsehr.service.sendToLims;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestBatchDTO;

@Service
public class SendToLimsBatchResolver {

    // Lawrence
    private static final Logger log = LoggerFactory.getLogger(SendToLimsBatchResolver.class);

    public UnifiedLimsRequestBatchDTO resolveBatch() {
        UnifiedLimsRequestBatchDTO analysisCase = new UnifiedLimsRequestBatchDTO();

        analysisCase.setCaseType("VL");
        analysisCase.setClientCaseID("");
        analysisCase.setReasonForVLtest("Routine Viral Load");
        analysisCase.setVLBreastFeeding(false);
        analysisCase.setVLPregnant(false);

        return analysisCase;
    }
}
