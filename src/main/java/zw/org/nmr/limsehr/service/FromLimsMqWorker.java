package zw.org.nmr.limsehr.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.service.dto.AcknowledgementFromLims;
import zw.org.nmr.limsehr.service.impl.LaboratoryRequestServiceImpl;

@Service
//@Transactional
public class FromLimsMqWorker {

    /**
     * @throws TimeoutException
     * @throws IOException
     * @date 08-10-2020
     * @writer Lawrence
     */
    @Autowired
    LaboratoryRequestServiceImpl laboratoryRequestServiceImpl;

    @Autowired
    private ObjectMapper mapper;

    @Value("${myConfig.myRabbitLocalGateway}")
    private String gateway;

    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange("from-lims"), key = "senaite", value = @Queue("from_lims")))
    public void receiveRequestFromLIMS(Message msg) throws IOException, TimeoutException {
        String string = new String(msg.getBody());
        msg.getMessageProperties().getHeader("request");

        System.out.println("Message --: " + msg);
        System.out.println("String --: " + string);

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AcknowledgementFromLims obj = mapper.readValue(string, AcknowledgementFromLims.class);

        laboratoryRequestServiceImpl.updateLaoratoryRequest(obj);
    }
}
