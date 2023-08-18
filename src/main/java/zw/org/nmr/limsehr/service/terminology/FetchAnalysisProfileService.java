package zw.org.nmr.limsehr.service.terminology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zw.org.nmr.limsehr.repository.AnalysisProfileRepository;
import zw.org.nmr.limsehr.service.dto.SampleTypeDTO;
import zw.org.nmr.limsehr.service.dto.laboratory.request.AnalysisProfile;
import zw.org.nmr.limsehr.service.dto.laboratory.request.AnalysisProfileDetail;

@Service
public class FetchAnalysisProfileService {

    private static final Logger log = LoggerFactory.getLogger(FetchAnalysisProfileService.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("$ {myConfig.myTerminologyGateway}")
    private String gateway;

    private final AnalysisProfileRepository analysisProfileRepository;

    public FetchAnalysisProfileService(AnalysisProfileRepository analysisProfileRepository) {
        this.analysisProfileRepository = analysisProfileRepository;
    }

    //@Scheduled(fixedRate = 50000)
    public SampleTypeDTO getPostWithResponseHandling() {
        boolean thereIsNext = true;

        String url = String.join(
            "/",
            gateway,
            "senaite/v1/analysisProfile?__ac_name=chirowodzal&sort_order=asc&__ac_password=password&limit=500"
        );

        while (thereIsNext) {
            ResponseEntity<AnalysisProfile> response = this.restTemplate.getForEntity(url, AnalysisProfile.class, 1);

            if (response.getStatusCode() == HttpStatus.OK) {
                // log.debug("Response from server {}", response);

                for (AnalysisProfileDetail data : response.getBody().getItems()) {
                    // data.getGetProvince();

                    log.debug("Titles from server {}", data.getTitle());
                    zw.org.nmr.limsehr.domain.AnalysisProfile analysisProfile = new zw.org.nmr.limsehr.domain.AnalysisProfile();

                    analysisProfile.setId(data.getId());
                    analysisProfile.setApi_url(data.getApi_url());
                    analysisProfile.setDescription(data.getDescription());
                    analysisProfile.setTitle(data.getTitle());
                    analysisProfile.setUid(data.getUid());
                    analysisProfile.setPath(data.getPath());
                    analysisProfile.setEffective(data.getEffective());
                    analysisProfile.setParent_uid(data.getParent_uid());

                    analysisProfileRepository.save(analysisProfile);
                }

                log.debug("The Next Fetch url ..... ..... : {}", response.getBody().getNext());

                if (response.getBody().getNext() != null) {
                    url = response.getBody().getNext();
                } else {
                    // thereIsNext = false;
                    break;
                }
                // return response.getBody();

            } else {
                return null;
            }
        } //
        return null;
    }
}
