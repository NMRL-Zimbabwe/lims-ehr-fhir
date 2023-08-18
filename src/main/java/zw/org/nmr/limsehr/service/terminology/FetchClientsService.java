package zw.org.nmr.limsehr.service.terminology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import zw.org.nmr.limsehr.domain.Client;
import zw.org.nmr.limsehr.repository.ClientRepository;
import zw.org.nmr.limsehr.service.dto.SenaiteClientDTO;
import zw.org.nmr.limsehr.service.dto.SenaiteClientDetailDTO;

@Service
public class FetchClientsService {

    private static final Logger log = LoggerFactory.getLogger(FetchClientsService.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("$ {myConfig.myTerminologyGateway}")
    private String gateway;

    private final ClientRepository clientRepository;

    public FetchClientsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //@Scheduled(fixedRate = 5000)
    public SenaiteClientDTO getPostWithResponseHandling() {
        boolean thereIsNext = true;

        log.debug("Gateway .......... : {}", gateway);

        String url = String.join(
            "/",
            gateway,
            "senaite/@@API/senaite/v1/client?__ac_name=chirowodzal&sort_order=asc&__ac_password=password&limit=500"
        );
        while (thereIsNext) {
            ResponseEntity<SenaiteClientDTO> response = this.restTemplate.getForEntity(url, SenaiteClientDTO.class, 1);

            if (response.getStatusCode() == HttpStatus.OK) {
                // log.debug("Response from server {}", response);

                for (SenaiteClientDetailDTO data : response.getBody().getItems()) {
                    // data.getGetProvince();

                    log.debug("Titles from server {}", data.getTitle());
                    Client client = new Client();

                    if (data.getGetClientID() != null) {
                        client.setId(data.getUid());
                        client.setClientId(data.getGetClientID());
                        client.setName(data.getTitle());
                        client.setPath(data.getPath());
                        client.setPortal_type(data.getPortal_type());

                        clientRepository.save(client);
                    }
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
