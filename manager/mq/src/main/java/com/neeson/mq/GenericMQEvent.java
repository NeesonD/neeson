package com.neeson.mq;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Create on 2020-03-20
 *
 * @author Administrator
 */
@Data
public abstract class GenericMQEvent  {

    public GenericMQEvent() {
        this.messageId = UUID.randomUUID().toString();
        this.createTime = LocalDateTime.now().toString();
    }

    private String messageId;

    private String createTime;

}
