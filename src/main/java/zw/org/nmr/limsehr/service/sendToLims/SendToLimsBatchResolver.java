package zw.org.nmr.limsehr.service.sendToLims;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.ReasonForTest;
import zw.org.nmr.limsehr.service.ReasonForTestService;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestBatchDTO;

@Service
public class SendToLimsBatchResolver {

    // Lawrence
    private static final Logger log = LoggerFactory.getLogger(SendToLimsBatchResolver.class);

    @Autowired
    ReasonForTestService reasonForTestService;

    public UnifiedLimsRequestBatchDTO resolveBatch(LaboratoryRequest request) {
        UnifiedLimsRequestBatchDTO analysisCase = new UnifiedLimsRequestBatchDTO();

        if (request.getReasonForTest() != null) {
            Optional<ReasonForTest> reasonForTest = reasonForTestService.getOne(request.getReasonForTest());
            reasonForTest.ifPresent(rft -> analysisCase.setReasonForVLtest(rft.getName()));
        }

        analysisCase.setClientCaseID("");
        analysisCase.setVLBreastFeeding(request.isBreastfeeding());
        analysisCase.setVLPregnant(request.isPregnant());

        // Auto resolve case type based on test
        // String caseType;
        analysisCase.setCaseType("VL");
        return analysisCase;
    }
}
