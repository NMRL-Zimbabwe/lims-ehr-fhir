package zw.org.nmr.limsehr.service.sendToLims;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.service.LaboratoryRequestService;
import zw.org.nmr.limsehr.service.LimsEhrMapperService;
import zw.org.nmr.limsehr.service.dto.EhrToLImsDTO;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestAnalysisRequestDTO;

@Service
public class SendToLimsSampleResolver {

    private static final Logger log = LoggerFactory.getLogger(SendToLimsSampleResolver.class);

    @Autowired
    LaboratoryRequestService laboratoryRequestService;

    @Autowired
    LimsEhrMapperService limsEhrMapperService;

    public UnifiedLimsRequestAnalysisRequestDTO resolveSample(LaboratoryRequest request, Laboratory laboratory) {
        UnifiedLimsRequestAnalysisRequestDTO analysisRequest = new UnifiedLimsRequestAnalysisRequestDTO();

        analysisRequest.setDateSampled(request.getDateCollected().toString());

        log.info("Routing key :{} ", laboratory.getRoutingKey());
        log.info("Sample Type :{} ", request.getSampleId());

        // Resolve Sample Type and Test by combination
        Optional<EhrToLImsDTO> mapping = limsEhrMapperService.resolveTestAndSampleType(
            laboratory.getId(),
            request.getTestId(),
            request.getSampleTypeId()
        );
        log.debug("Sample Type and Test response :{}", mapping);
        if (mapping.isEmpty()) {
            log.error("SampleType and Test not found");
            laboratoryRequestService.flushOurErrorsFromQueue(request, "SampleType and Test not found");
            return null;
        }
        EhrToLImsDTO testAndSampleType = mapping.get();

        analysisRequest.setSampleType(testAndSampleType.getSampleType().getName());

        List<String> testNames = new ArrayList<>();
        testNames.add(testAndSampleType.getTest().getName());
        //
        analysisRequest.setProfiles(testNames.toArray(new String[0]));

        if (request.getClientSampleId() != null) {
            analysisRequest.setClientSampleId(request.getClientSampleId());
        }
        analysisRequest.setContact("Sister in charge");
        analysisRequest.setTemplate("");

        return analysisRequest;
    }
}
