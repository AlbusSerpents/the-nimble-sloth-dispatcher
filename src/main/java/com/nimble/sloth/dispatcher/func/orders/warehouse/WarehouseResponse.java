package com.nimble.sloth.dispatcher.func.orders.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class WarehouseResponse {

    @JsonProperty(value = "_id", required = true)
    private String _id;

    private Object order;
}
