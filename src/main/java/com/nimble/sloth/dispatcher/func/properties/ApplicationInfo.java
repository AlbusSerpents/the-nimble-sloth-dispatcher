package com.nimble.sloth.dispatcher.func.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class ApplicationInfo {
    private String appId;
    private String url;
    private String token;
}
