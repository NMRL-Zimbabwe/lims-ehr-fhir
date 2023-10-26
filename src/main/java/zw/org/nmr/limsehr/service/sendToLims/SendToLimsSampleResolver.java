package zw.org.nmr.limsehr.service.sendToLims;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.SampleType;
import zw.org.nmr.limsehr.service.LaboratoryRequestService;
import zw.org.nmr.limsehr.service.SampleTypeMapperService;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestAnalysisRequestDTO;

@Service
public class SendToLimsSampleResolver {

    private static final Logger log = LoggerFactory.getLogger(SendToLimsSampleResolver.class);

    @Autowired
    LaboratoryRequestService laboratoryRequestService;

    @Autowired
    SampleTypeMapperService sampleTypeMapperService;

    public UnifiedLimsRequestAnalysisRequestDTO resolveSample(LaboratoryRequest request, Laboratory laboratory) {
        UnifiedLimsRequestAnalysisRequestDTO analysisRequest = new UnifiedLimsRequestAnalysisRequestDTO();

        //    analysisRequest.setDateSampled(LocalDate.now().toString());
        //    analysisRequest.setDateSampled(request.getDateCollected().toString());

        String[] profiles = new String[] { "Viral Load Plasma" };

        analysisRequest.setProfiles(profiles);

        log.info("Routing key :{} ", laboratory.getRoutingKey());
        log.info("Sample Type :{} ", request.getSampleId());

        Optional<SampleType> sampleType = sampleTypeMapperService.resolveSampleTypeName(laboratory.getId(), request.getSampleTypeId());
        log.debug("Sample Type response :{}", sampleType);
        if (sampleType.isEmpty()) {
            log.error("No sample type was found");
            laboratoryRequestService.flushOurErrorsFromQueue(request, "No sample type was found");
            return null;
        }
        analysisRequest.setSampleType(sampleType.get().getName());
        analysisRequest.setContact("Sister in charge");

        if (request.getClientSampleId() != null) {
            analysisRequest.setClientSampleId(request.getClientSampleId());
        }

        analysisRequest.setTemplate("");

        return analysisRequest;
    }
}
