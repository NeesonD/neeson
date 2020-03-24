package com.neeson.mq.cache;

import com.neeson.mq.GenericMQEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.neeson.common.constant.LogConstant.LOG_PRE;

/**
 * @author neeson
 */
@Slf4j
public class LocalMessageCache implements IMessageCache {

    private Map<String, GenericMQEvent> messageCache = new ConcurrentSkipListMap<>();
    private Map<String, GenericMQEvent> failedMessageCache = new ConcurrentSkipListMap<>();


    @Override
    public void add(GenericMQEvent event) {
        log.info(LOG_PRE +  Thread.currentThread() .getStackTrace()[1].getMethodName() + event.getMessageId());
        Assert.hasLength(event.getMessageId(), "MQEvent MessageId not null");
        messageCache.put(event.getMessageId(), event);
    }

    @Override
    public void sendSuccess(String id) {
        log.info(LOG_PRE +  Thread.currentThread().getStackTrace()[1].getMethodName() + id);
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
