package com.nimble.sloth.dispatcher.func.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServerProperty {
    private String key;
    private String value;
}
