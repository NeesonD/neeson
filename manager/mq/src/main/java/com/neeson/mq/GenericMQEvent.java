package com.neeson.mq;


import com.neeson.common.utils.IdGenerator;

import java.time.LocalTime;

/**
 * Create on 2020-03-20
 *
 * @author Administrator
 */
public abstract class GenericMQEvent<T>  {

    private String messageId;

    private String createTime;

    private T eventSource;

    private IdGenerator idGenerator;

    public GenericMQEvent(T eventSource) {
        this.eventSource = eventSource;
        this.createTime = LocalTime.now().toString();
        this.messageId = idGenerator.generateId();
    }

    public String getMessageId() {
        return messageId;
    }


}
