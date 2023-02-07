package org.dyson.chat.entities;

import org.dyson.chat.core.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Member extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "username")
    public User user;
    @ManyToOne
    public Chat chat;
    public String type;
    public String data;
}
