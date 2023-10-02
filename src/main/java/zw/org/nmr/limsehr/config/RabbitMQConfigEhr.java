package zw.org.nmr.limsehr.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfigEhr {

    // Senaite config
    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    int port;

    @Value("${rabbitmq.virtualHost}")
    private String vhost;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    // EHR Config

    private String QUEUE = "lab.services";

    private String EXCHANGE = "amq.direct";

    private String ROUTING_KEY = "ehr.lims";

    /*
     * @Bean Queue queue() { return new Queue(QUEUE, true); }
     *
     * @Bean DirectExchange exchange() { return new DirectExchange(EXCHANGE); }
     *
     * @Bean Binding binding(Queue queue, DirectExchange exchange) { return
     * BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY); }
     */

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "senaiteContainerFactory")
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean(name = "ehrContainerFactory")
    public SimpleRabbitListenerContainerFactory ehrContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //int i = Integer.parseInt(port);
        CachingConnectionFactory senaiteConnectionFactory = new CachingConnectionFactory(host, 4056);
        senaiteConnectionFactory.setUsername(username);
        senaiteConnectionFactory.setPassword(password);
        senaiteConnectionFactory.setVirtualHost(vhost);
        factory.setConnectionFactory(senaiteConnectionFactory);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }
    /* *//**
     * Required for executing adminstration functions against an AMQP Broker
     */
    /*
     * @Bean(name="labServices") public AmqpAdmin amqpAdmin(ConnectionFactory
     * connectionFactory) { return new RabbitAdmin(connectionFactory); }
     *
     *//**
     * This queue will be declared. This means it will be created if it does not
     * exist. Once declared, you can do something like the following:
     *
     * @RabbitListener(queues = "#{@myDurableQueue}")
     * @Transactional public void handleMyDurableQueueMessage(CustomDurableDto
     *                myMessage) { // Anything you want! This can also return a
     *                non-void which will queue it back in to the queue attached
     *                to @RabbitListener }
     */
    /*
     * @Bean public Queue myDurableQueue() { // This queue has the following
     * properties: // name: my_durable // durable: true // exclusive: false //
     * auto_delete: false return new Queue("Marondera", true, false, false); }
     *
     *//**
     * The following is a complete declaration of an exchange, a queue and a
     * exchange-queue binding
     *//*
     * @Bean public TopicExchange emailExchange() { return new
     * TopicExchange("ehr-lims", true, false); }
     *
     * @Bean public Queue inboundEmailQueue() { return new Queue("NMRL", true,
     * false, false); }
     *
     * @Bean public org.springframework.amqp.core.Binding
     * inboundEmailExchangeBinding() { // Important part is the routing key -- this
     * is just an example return
     * BindingBuilder.bind(inboundEmailQueue()).to(emailExchange()).with("nmrl.*");
     * }
     */

}
