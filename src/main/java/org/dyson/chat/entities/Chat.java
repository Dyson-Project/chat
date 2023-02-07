package org.dyson.chat.entities;

import org.dyson.chat.core.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Chat extends AbstractEntity {
    @Id
    public Long id;
    public String title;
    public String type;

    public String data;
}
