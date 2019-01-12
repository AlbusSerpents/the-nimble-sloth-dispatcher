package com.nimble.sloth.dispatcher.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import static java.lang.System.getenv;

@EnableRabbit
@Configuration
public class QueueConfig {

    private static final String QUEUE_TOPIC_NAME_KEY = "queue-topic";
    private static final String QUEUE_ROUTING_KEY = "queue-routing";
    private static final String QUEUE_QUEUE_NAME_KEY = "queue-name";

    private static final String QUEUE_USER_KEY = "queue-user";
    private static final String QUEUE_PASSWORD_KEY = "queue-password";
    private static final String QUEUE_CONNECTION_STRING_TEMPLATE = "amqp://%s:%s@bee.rmq.cloudamqp.com/%s";

    @Bean
    public ConnectionFactory connectionFactory() {
        final String queueUser = getenv(QUEUE_USER_KEY);
        final String queuePassword = getenv(QUEUE_PASSWORD_KEY);
        final String uriString = String.format(QUEUE_CONNECTION_STRING_TEMPLATE, queueUser, queuePassword, queueUser);

        try {
            final URI uri = new URI(uriString);
            return new CachingConnectionFactory(uri);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final @Autowired ConnectionFactory connectionFactory) {
        final String queueName = getenv(QUEUE_QUEUE_NAME_KEY);
        final String exchangeName = getenv(QUEUE_TOPIC_NAME_KEY);
        final String routingKey = getenv(QUEUE_ROUTING_KEY);

        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setDefaultReceiveQueue(queueName);
        template.setExchange(exchangeName);
        template.setRoutingKey(routingKey);

        return template;
    }
}
