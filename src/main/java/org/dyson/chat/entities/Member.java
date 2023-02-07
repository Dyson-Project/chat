package org.dyson.chat.entities;

import org.dyson.chat.core.AbstractEntity;
import org.dyson.chat.enums.MemberType;

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
    public MemberType type;
    public String data;
}
