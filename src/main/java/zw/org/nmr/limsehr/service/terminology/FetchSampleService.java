package zw.org.nmr.limsehr.service.terminology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zw.org.nmr.limsehr.domain.SampleType;
import zw.org.nmr.limsehr.repository.SampleTypeRepository;
import zw.org.nmr.limsehr.service.dto.SampleTypeDTO;
import zw.org.nmr.limsehr.service.dto.SampleTypeDetailDTO;

@Service
public class FetchSampleService {

    private static final Logger log = LoggerFactory.getLogger(FetchSampleService.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("$ {myConfig.myTerminologyGateway}")
    private String gateway;

    private final SampleTypeRepository sampleTypeRepository;

    public FetchSampleService(SampleTypeRepository sampleTypeRepository) {
        this.sampleTypeRepository = sampleTypeRepository;
    }

    //@Scheduled(fixedRate = 50000)
    public SampleTypeDTO getPostWithResponseHandling() {
        boolean thereIsNext = true;

        String url = String.join(
            "/",
            gateway,
            "senaite/@@API/senaite/v1/sampleType?__ac_name=chirowodzal&sort_order=asc&__ac_password=password&limit=500"
        );

        while (thereIsNext) {
            ResponseEntity<SampleTypeDTO> response = this.restTemplate.getForEntity(url, SampleTypeDTO.class, 1);

            if (response.getStatusCode() == HttpStatus.OK) {
                // log.debug("Response from server {}", response);

                for (SampleTypeDetailDTO data : response.getBody().getItems()) {
                    //data.getGetProvince();

                    log.debug("Titles from server {}", data.getTitle());
                    SampleType sampleType = new SampleType();

                    sampleType.setId(data.getUid());
                    sampleType.setName(data.getTitle());
                    sampleType.setSampleId(data.getId());

                    sampleTypeRepository.save(sampleType);
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
