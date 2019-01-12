package com.nimble.sloth.dispatcher.func.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.nimble.sloth.dispatcher.func.queue.QueueMessage.emptyMessage;
import static java.util.Optional.ofNullable;

@Service
public class QueueService {

    private final RabbitTemplate template;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueueService(final RabbitTemplate template) {
        this.template = template;
    }

    public QueueMessageResponse send(final QueueMessage queueMessage) {
        try {
            final String message = mapper.writeValueAsString(queueMessage);
            System.out.println("Sending: " + message);
            final MessageProperties properties = new MessageProperties();

            template.send(new Message(message.getBytes(), properties));
            return new QueueMessageResponse(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new QueueMessageResponse(false);
        }
    }

    public QueueMessage receive() {
        return ofNullable(template.receive())
                .map(Message::getBody)
                .map(String::new)
                .map(this::deserialize)
                .orElseGet(QueueMessage::emptyMessage);
    }

    private QueueMessage deserialize(final String message) {
        try {
            final QueueMessage queueMessage = mapper.readValue(message, QueueMessage.class);
            System.out.println("Received: " + queueMessage);
            return queueMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return emptyMessage();
        }
    }
}
