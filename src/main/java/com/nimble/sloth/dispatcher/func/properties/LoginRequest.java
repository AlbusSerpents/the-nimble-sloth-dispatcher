package com.nimble.sloth.dispatcher.func.properties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class LoginRequest {
    private final String appId;
    private final String token;
}
