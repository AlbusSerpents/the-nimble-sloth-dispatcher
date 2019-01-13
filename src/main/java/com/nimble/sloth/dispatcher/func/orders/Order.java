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
    private OrderLocation pickUp;

    @Valid
    @NotNull
    private OrderLocation destination;

    @JsonIgnore
    private boolean sentForPickUp;

    @JsonIgnore
    private boolean sentForDelivery;

}
