package org.dyson.chat.core;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity extends AbstractAuditEntity {
    @Id
    Long id;
}
