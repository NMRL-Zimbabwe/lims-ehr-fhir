package zw.org.nmr.limsehr.service.terminology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zw.org.nmr.limsehr.domain.Test;
import zw.org.nmr.limsehr.repository.TestTypeRepository;
import zw.org.nmr.limsehr.service.dto.SampleTypeDTO;
import zw.org.nmr.limsehr.service.dto.TestTypeDTO;
import zw.org.nmr.limsehr.service.dto.TestTypeDetailDTO;

@Service
public class FetchTestService {

    private static final Logger log = LoggerFactory.getLogger(FetchTestService.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("$ {myConfig.myTerminologyGateway}")
    private String gateway;

    private final TestTypeRepository testTypeRepository;

    public FetchTestService(TestTypeRepository testTypeRepository) {
        this.testTypeRepository = testTypeRepository;
    }

    //@Scheduled(fixedRate = 5000)
    public SampleTypeDTO getPostWithResponseHandling() {
        boolean thereIsNext = true;

        String url = String.join(
            "/",
            gateway,
            "senaite/@@API/senaite/v1/analysisprofile?__ac_name=chirowodzal&sort_order=asc&__ac_password=password&limit=500"
        );

        while (thereIsNext) {
            ResponseEntity<TestTypeDTO> response = this.restTemplate.getForEntity(url, TestTypeDTO.class, 1);

            if (response.getStatusCode() == HttpStatus.OK) {
                // log.debug("Response from server {}", response);

                for (TestTypeDetailDTO data : response.getBody().getItems()) {
                    //data.getGetProvince();

                    log.debug("Titles from server {}", data.getTitle());
                    Test test = new Test();

                    test.setId(data.getUid());
                    test.setName(data.getTitle());
                    test.setTestId(data.getId());

                    testTypeRepository.save(test);
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
