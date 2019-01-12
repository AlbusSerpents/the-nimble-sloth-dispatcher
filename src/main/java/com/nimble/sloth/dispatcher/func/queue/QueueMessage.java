package com.nimble.sloth.dispatcher.func.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueMessage {
    @NotBlank
    private String name;
    @NotBlank
    private String message;

    static QueueMessage emptyMessage() {
        return new QueueMessage(null, null);
    }
}
