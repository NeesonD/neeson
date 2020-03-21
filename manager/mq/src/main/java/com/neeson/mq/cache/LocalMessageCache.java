package com.neeson.mq.cache;

import com.neeson.mq.GenericMQEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author neeson
 */
@Slf4j
public class LocalMessageCache implements IMessageCache {

    private Map<String, GenericMQEvent> messageCache = new ConcurrentSkipListMap<>();
    private Map<String, GenericMQEvent> failedMessageCache = new ConcurrentSkipListMap<>();


    @Override
    public void add(GenericMQEvent event) {
        Assert.hasLength(event.getMessageId(), "MQEvent MessageId not null");
        messageCache.put(event.getMessageId(), event);
    }

    @Override
    public void sendSuccess(String id) {
        messageCache.remove(id);
    }

    @Override
    public void sendFailed(String id, String cause) {
        log.error("消息发送失败：" + id + " | " + cause);
        failedMessageCache.put(id, messageCache.get(id));
    }

    @Override
    public void consumptionFailure(String id) {

    }

    @Override
    public void complete(String id) {

    }

}
