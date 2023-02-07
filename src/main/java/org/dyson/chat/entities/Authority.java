package org.dyson.chat.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "authorities",
        indexes = {
                @Index(columnList = "username, authority", unique = true)
        }
)
public class Authority implements GrantedAuthority {

    @Id
    @Column(name = "authority")
    private String role;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public Authority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public Authority() {
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Authority) {
            return this.role.equals(((Authority) obj).role);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.role.hashCode();
    }

    @Override
    public String toString() {
        return this.role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

}
