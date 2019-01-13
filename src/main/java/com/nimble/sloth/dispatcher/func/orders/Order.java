package com.nimble.sloth.dispatcher.func.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @NotBlank
    private String orderId;

    @Valid
    @NotNull
    private Location pickUp;

    @Valid
    @NotNull
    private Location destination;

    @JsonIgnore
    private boolean sentForPickUp;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Location {
        @NotNull
        private BigDecimal latitude;
        @NotNull
        private BigDecimal longitude;
    }

    @Data
    @AllArgsConstructor(access = PRIVATE)
    static class Delivery {
        private final String orderId;
        private final Location pickUp;
        private final Location destination;

        static Delivery toWarehouse(
                final Order order,
                final Location warehouse) {
            final String orderId = order.getOrderId();
            final Location pickUp = order.getPickUp();
            return new Delivery(orderId, pickUp, warehouse);
        }

        static Delivery fromWarehouse(
                final Order order,
                final Location warehouse) {
            final String orderId = order.getOrderId();
            final Location destination = order.getDestination();
            return new Delivery(orderId, warehouse, destination);
        }
    }
}
