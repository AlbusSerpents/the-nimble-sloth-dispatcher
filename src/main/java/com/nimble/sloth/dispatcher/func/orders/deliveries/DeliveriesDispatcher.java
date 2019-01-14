package com.nimble.sloth.dispatcher.func.orders.deliveries;

import com.nimble.sloth.dispatcher.func.queue.QueueMessageResponse;
import com.nimble.sloth.dispatcher.func.queue.QueueService;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.logging.LogFactory.getLog;

@Service
public class DeliveriesDispatcher {

    private final Log log = getLog(DeliveriesDispatcher.class);

    private final QueueService queueService;
    private final DeliveriesCoordinator coordinator;

    public DeliveriesDispatcher(
            final QueueService queueService,
            final DeliveriesCoordinator coordinator) {
        this.queueService = queueService;
        this.coordinator = coordinator;
    }

    public boolean sendDeliveries(
            final List<Delivery> to,
            final List<Delivery> from) {
        final List<Delivery> allDeliveries = new ArrayList<>();
        allDeliveries.addAll(to);
        allDeliveries.addAll(from);

        if (allDeliveries.isEmpty()) {
            return false;
        } else {
            final boolean success = coordinator
                    .groupDeliveries(allDeliveries)
                    .parallelStream()
                    .filter(a -> !a.isEmpty())
                    .map(queueService::send)
                    .allMatch(QueueMessageResponse::isSuccess);

            final String message = String.format("Sending to with result %s", success);
            log.info(message);
            return success;
        }

    }
}
