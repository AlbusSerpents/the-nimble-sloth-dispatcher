package com.nimble.sloth.dispatcher.func.orders.deliveries;

import com.nimble.sloth.dispatcher.func.orders.OrderLocation;
import com.nimble.sloth.dispatcher.func.orders.OrdersRepository;
import com.nimble.sloth.dispatcher.func.properties.PropertiesService;
import org.apache.commons.logging.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static com.nimble.sloth.dispatcher.func.properties.PropertiesKey.WAREHOUSE_LATITUDE;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.logging.LogFactory.getLog;

@Service
public class DeliveriesScheduler {

    private final Log log = getLog(DeliveriesScheduler.class);

    private static final long ONE_MINUTES_IN_MILLIS = 60 * 1000;

    private final DeliveriesDispatcher dispatcher;
    private final DeliveriesCreator creator;
    private final PropertiesService propertiesService;
    private final OrdersRepository repository;

    public DeliveriesScheduler(
            final DeliveriesDispatcher dispatcher,
            final DeliveriesCreator creator,
            final PropertiesService propertiesService,
            final OrdersRepository repository) {
        this.dispatcher = dispatcher;
        this.creator = creator;
        this.propertiesService = propertiesService;
        this.repository = repository;
    }

    @Scheduled(fixedDelay = ONE_MINUTES_IN_MILLIS)
    public void scheduleDeliveries() {
        final String startMessage = String.format("Starting deliveries schedule at %s", now());
        log.info(startMessage);

        final OrderLocation warehouse = warehouseLocation();
        final List<Delivery> fromWarehouse = creator.fromWarehouseDeliveries(warehouse);
        final List<Delivery> toWarehouse = creator.toWarehouseDeliveries(warehouse);

        final Set<String> toWarehouseIds = getOrderIds(toWarehouse);
        final Set<String> fromWarehouseIds = getOrderIds(fromWarehouse);

        final boolean anythingSent = dispatcher.sendDeliveries(toWarehouse, fromWarehouse);
        if (anythingSent) {
            toWarehouseIds.forEach(repository::markAsSentForPickup);
            fromWarehouseIds.forEach(repository::markAsSentForDelivery);

            final String endMessage = String.format("Ending deliveries dispatching at %s", now());
            log.info(endMessage);
        } else {
            log.info("Nothing dispatched");
        }
    }

    private OrderLocation warehouseLocation() {
        final String warehouseLatitude = propertiesService.getRequiredProperty(WAREHOUSE_LATITUDE);
        final String warehouseLongitude = propertiesService.getRequiredProperty(WAREHOUSE_LATITUDE);

        final BigDecimal latitude = new BigDecimal(warehouseLatitude);
        final BigDecimal longitude = new BigDecimal(warehouseLongitude);

        return new OrderLocation(latitude, longitude);
    }

    private Set<String> getOrderIds(final List<Delivery> deliveries) {
        return deliveries
                .stream()
                .map(Delivery::getOrderId)
                .collect(toSet());
    }
}
