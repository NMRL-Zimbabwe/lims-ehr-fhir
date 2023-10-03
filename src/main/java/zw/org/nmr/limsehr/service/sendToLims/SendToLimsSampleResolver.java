package zw.org.nmr.limsehr.service.sendToLims;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.service.LaboratoryRequestService;
import zw.org.nmr.limsehr.service.dto.unified.lims_request.UnifiedLimsRequestAnalysisRequestDTO;

@Service
public class SendToLimsSampleResolver {

    private static final Logger log = LoggerFactory.getLogger(SendToLimsSampleResolver.class);

    @Autowired
    LaboratoryRequestService laboratoryRequestService;

    public UnifiedLimsRequestAnalysisRequestDTO resolveSample(LaboratoryRequest request, String destination) {
        UnifiedLimsRequestAnalysisRequestDTO analysisRequest = new UnifiedLimsRequestAnalysisRequestDTO();

        analysisRequest.setDateSampled(request.getDateCollected().toString());

        String[] profiles = new String[] { "Viral Load Plasma" };

        analysisRequest.setProfiles(profiles);

        String sampleType = null;
        log.info("Routing key :{} ", destination);
        log.info("Sample Type :{} ", request.getSampleId());

        //        List<IDestinationLab> activelabs = null;
        //        try {
        //            // create object mapper instance
        //            ObjectMapper mapper = new ObjectMapper();
        //
        //            // convert JSON array to list of destinatios
        //            activelabs = Arrays.asList(mapper.readValue(Paths.get("activated.json").toFile(), IDestinationLab[].class));
        //
        //            // print books
        //            activelabs.forEach(System.out::println);
        //
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //        }
        //
        //        if (activelabs == null) { // there are not active labs
        //            return null;
        //        }
        //        Optional<IDestinationLab> lab = activelabs.stream().filter(c -> c.getTitle().equals(destination)).findAny();

        switch (destination) {
            /**
             * Sample types are hard coded at the moment because, different laboratories use
             * different default sample types. For example BRIDH register samples as Blood
             * plasma whilst Mpilo registers as whole blood.
             *
             */

            case "bridh":
            case "epworth":
            case "chinhoyi":
            case "kadoma":
            case "nmrl":
                if (request.getSampleId().equals("DBS")) {
                    sampleType = "Dried Blood Spot";
                } else {
                    sampleType = "Blood plasma";
                }
                break;
            case "mpilo":
                if (request.getSampleId().equals("DBS")) {
                    sampleType = "DBS";
                } else {
                    sampleType = "Whole blood";
                }
                break;
            case "marondera":
                if (request.getSampleId().equals("DBS")) {
                    sampleType = "DBS";
                } else {
                    sampleType = "Blood plasma";
                }
                break;
        }
        log.info("Sample Type after :{} ", sampleType);

        if (sampleType == null) {
            log.error("No sample type was found");
            laboratoryRequestService.flushOurErrorsFromQueue(request, "No sample type was found");
            return null;
        }
        analysisRequest.setSampleType(sampleType);
        analysisRequest.setContact("Sister in charge");

        if (request.getClientSampleId() != null) {
            analysisRequest.setClientSampleId(request.getClientSampleId());
        }

        analysisRequest.setTemplate("");

        return analysisRequest;
    }
}
