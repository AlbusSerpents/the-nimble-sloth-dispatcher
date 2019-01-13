package com.nimble.sloth.dispatcher.func.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLocation {
    @NotNull
    private BigDecimal latitude;
    @NotNull
    private BigDecimal longitude;
}
