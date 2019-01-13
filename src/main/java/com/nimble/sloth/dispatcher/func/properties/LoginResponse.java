package com.nimble.sloth.dispatcher.func.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class LoginResponse {
    private String sessionId;
    private String appId;
    private String token;
}
