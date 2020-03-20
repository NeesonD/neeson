package com.neeson.mq;

import cn.hutool.core.date.DateTime;
import com.neeson.common.context.trace.TraceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Create on 2020-03-20
 *
 * @author Administrator
 */
@Slf4j
@Component
@Import(value = Jackson2JsonMessageConverter.class)
public class RabbitMqProducer implements MqProducer, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Value("${defaultExchangeName}")
    private String defaultExchangeName;

    @Autowired
    private Jackson2JsonMessageConverter converter;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TraceContextHolder traceContextHolder;


    @Override
    public void publishEvent(GenericMQEvent event) {
        Message message = assembleMessage(event);
        String routeKey = getRouteKey(event);
        rabbitTemplate.convertAndSend(defaultExchangeName, routeKey, message);
    }

    private Message assembleMessage(GenericMQEvent e) {
        MessageProperties properties = new MessageProperties();
        properties.setTimestamp(new DateTime());
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        properties.setCorrelationId(traceContextHolder.get().getTraceId());
        return converter.toMessage(e, properties);
    }

    private String getRouteKey(GenericMQEvent e) {
        return e.getClass().getSimpleName();
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {

    }
}
