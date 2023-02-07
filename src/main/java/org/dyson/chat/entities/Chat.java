package org.dyson.chat.entities;

import lombok.NoArgsConstructor;
import org.dyson.chat.core.AbstractEntity;
import org.dyson.chat.enums.ChatType;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
public class Chat extends AbstractEntity {
    @Id
    public Long id;
    public String title;
    @Enumerated(EnumType.STRING)
    public ChatType type;
    public String data;

    public Chat(String title, ChatType type) {
        this.title = title;
        this.type = type;
    }

}
