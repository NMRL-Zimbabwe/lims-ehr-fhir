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
import zw.org.nmr.limsehr.domain.SampleType;
import zw.org.nmr.limsehr.domain.Test;
import zw.org.nmr.limsehr.service.LaboratoryRequestService;
import zw.org.nmr.limsehr.service.SampleTypeMapperService;
import zw.org.nmr.limsehr.service.TestMapperService;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestAnalysisRequestDTO;

@Service
public class SendToLimsSampleResolver {

    private static final Logger log = LoggerFactory.getLogger(SendToLimsSampleResolver.class);

    @Autowired
    LaboratoryRequestService laboratoryRequestService;

    @Autowired
    SampleTypeMapperService sampleTypeMapperService;

    @Autowired
    TestMapperService testMapperService;

    public UnifiedLimsRequestAnalysisRequestDTO resolveSample(LaboratoryRequest request, Laboratory laboratory) {
        UnifiedLimsRequestAnalysisRequestDTO analysisRequest = new UnifiedLimsRequestAnalysisRequestDTO();

        analysisRequest.setDateSampled(request.getDateCollected().toString());

        log.info("Routing key :{} ", laboratory.getRoutingKey());
        log.info("Sample Type :{} ", request.getSampleId());

        // Resolve Sample Type
        Optional<SampleType> sampleType = sampleTypeMapperService.resolveSampleTypeName(laboratory.getId(), request.getSampleTypeId());
        log.debug("Sample Type response :{}", sampleType);
        if (sampleType.isEmpty()) {
            log.error("SampleType NOT found");
            laboratoryRequestService.flushOurErrorsFromQueue(request, "SampleType NOT found");
            return null;
        }
        analysisRequest.setSampleType(sampleType.get().getName());
        analysisRequest.setContact("Sister in charge");

        if (request.getClientSampleId() != null) {
            analysisRequest.setClientSampleId(request.getClientSampleId());
        }
        analysisRequest.setTemplate("");

        // Resolve Tests Hard coded Viral load
        // String[] profiles = new String[] { "Viral Load Plasma" };
        // analysisRequest.setProfiles(profiles);

        // auto resolve by test name and sample type and or laboratory
        Optional<Test> resolvedTest = testMapperService.resolveTestName(laboratory.getId(), request.getTestId(), sampleType.get().getId());
        log.debug("Test response :{}", resolvedTest);
        if (resolvedTest.isEmpty()) {
            log.error("Requested Test NOT found");
            laboratoryRequestService.flushOurErrorsFromQueue(request, "Requested Test NOT found");
            return null;
        }
        List<String> testNames = new ArrayList<>();
        testNames.add(resolvedTest.get().getName());
        //
        analysisRequest.setProfiles(testNames.toArray(new String[0]));

        return analysisRequest;
    }
}
