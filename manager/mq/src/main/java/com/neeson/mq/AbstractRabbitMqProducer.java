package com.neeson.mq;

import cn.hutool.core.date.DateTime;
import com.neeson.common.context.trace.TraceContextHolder;
import com.neeson.mq.cache.IMessageCache;
import com.neeson.mq.cache.LocalMessageCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * Create on 2020-03-20
 *
 * @author Administrator
 */
@Slf4j
public abstract class AbstractRabbitMqProducer implements MqProducer, RabbitTemplate.ConfirmCallback {

    private MessageConverter converter;

    @Autowired
    protected RabbitTemplate rabbitTemplate;

    protected TraceContextHolder traceContextHolder;

    protected IMessageCache messageCache = new LocalMessageCache();

    private static final String TRACE_ID = "TRACE_ID";

    public AbstractRabbitMqProducer() {
        this.converter = new Jackson2JsonMessageConverter();
    }

    /**
     * 现将事件保存在缓存里面
     *
     * @param event
     */
    @Override
    public void publishEvent(GenericMQEvent event) {
        messageCache.add(event);
        Message message = assembleMessage(event);
        String routeKey = getRouteKey(event);
        sendMessage(message, routeKey);
    }

    public abstract void sendMessage(Message message, String routeKey);

    /**
     * 组装消息
     *
     * @param e
     * @return
     */
    public Message assembleMessage(GenericMQEvent e) {
        Assert.notNull(e.getMessageId(), "消息 ID 不为空");
        MessageProperties properties = new MessageProperties();
        properties.setTimestamp(new DateTime());
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        properties.setCorrelationId(e.getMessageId());
        properties.setHeader(TRACE_ID, traceContextHolder.get().getTraceId());
        return converter.toMessage(e, properties);
    }

    public String getRouteKey(GenericMQEvent e) {
        return e.getClass().getSimpleName();
    }

    /**
     * 消息发送确认机制
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String messageId = correlationData.getId();
        if (ack) {
            messageCache.sendSuccess(messageId);
        } else {
            messageCache.sendFailed(messageId, cause);
        }
    }

}
