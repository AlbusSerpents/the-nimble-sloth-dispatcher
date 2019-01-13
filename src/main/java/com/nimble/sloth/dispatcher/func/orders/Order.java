package com.nimble.sloth.dispatcher.func.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @NotBlank
    private String orderId;

    @Valid
    @NotNull
    private Location pickUp;

    @Valid
    @NotNull
    private Location destination;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        @NotNull
        private BigDecimal latitude;
        @NotNull
        private BigDecimal longitude;
    }

    @Data
    @AllArgsConstructor
    public static class Delivery {
        private final String orderId;
        private final Location location;
    }
}
