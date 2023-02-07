package org.dyson.chat.entities;

import lombok.NoArgsConstructor;
import org.dyson.chat.core.AbstractEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document
public class Message extends AbstractEntity {
    public String username;
    public String chatId;
    public String content;
    public String data;
    public Message(String username, String content) {
        this.username = username;
        this.content = content;
    }
}
