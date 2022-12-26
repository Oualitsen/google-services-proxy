package com.pinitservices.proxy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;

@Data
@EqualsAndHashCode(of = "id")
@FieldNameConstants
public class BasicEntity {

    @Id
    protected String id;

    protected long creationDate;
    protected long lastUpdate;

}
