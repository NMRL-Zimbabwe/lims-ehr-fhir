package zw.org.nmr.limsehr.service.sendToLims;

import java.time.LocalDate;
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

        analysisRequest.setDateSampled(LocalDate.now().toString());
        //    analysisRequest.setDateSampled(request.getDateCollected().toString());

        String[] profiles = new String[] { "Viral Load Plasma" };

        analysisRequest.setProfiles(profiles);

        log.info("Routing key :{} ", laboratory.getType());
        log.info("Sample Type :{} ", request.getSampleId());

        Optional<SampleType> sampleType = sampleTypeMapperService.resolveSampleTypeName(laboratory.getId(), request.getSampleTypeName());

        //        switch (laboratory.getType()) {
        //            /**
        //             * Sample types are hard coded at the moment because, different laboratories use
        //             * different default sample types. For example BRIDH register samples as Blood
        //             * plasma whilst Mpilo registers as whole blood
        //             */
        //
        //            case "bridh":
        //            case "epworth":
        //            case "chinhoyi":
        //            case "kadoma":
        //            case "nmrl":
        //                if (request.getSampleTypeName().equals("DBS")) {
        //                    sampleType = "Dried Blood Spot";
        //                } else {
        //                    sampleType = "Blood plasma";
        //                }
        //                break;
        //            case "mpilo":
        //                if (request.getSampleTypeName().equals("DBS")) {
        //                    sampleType = "DBS";
        //                } else {
        //                    sampleType = "Whole blood";
        //                }
        //                break;
        //            case "marondera":
        //                if (request.getSampleTypeName().equals("DBS")) {
        //                    sampleType = "DBS";
        //                } else {
        //                    sampleType = "Blood plasma";
        //                }
        //                break;
        //        }
        //        log.info("Sample Type after :{} ", sampleType);

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
