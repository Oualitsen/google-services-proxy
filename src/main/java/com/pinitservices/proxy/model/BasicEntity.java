package com.pinitservices.proxy.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;

@Data
@FieldNameConstants
public class BasicEntity {

    @Id
    protected String id;

    protected long creationDate;
    protected long lastUpdate;

}
