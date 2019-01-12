package com.nimble.sloth.dispatcher.func.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatus {
    private String applicationName;
    private String applicationAddress;
    private String applicationStatus;
}
