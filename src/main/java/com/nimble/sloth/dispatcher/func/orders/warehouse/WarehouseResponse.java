package com.nimble.sloth.dispatcher.func.orders.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
class WarehouseResponse {

    private String _id;

    @NotBlank
    private String id;

    private Object order;
}
