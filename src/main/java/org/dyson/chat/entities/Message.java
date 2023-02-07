package org.dyson.chat.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Message {
    private String from;

    private String content;

    public Message(String from, String content) {
        this.from = from;
        this.content = content;
    }
}
