package com.neeson.account.mq;

import com.neeson.mq.AbstractRabbitMqProducer;
import com.neeson.mq.GenericMQEvent;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.neeson.account.mq.RabbitMqConstant.USER_EXCHANGE;

/**
 * @author neeson
 */
@Component
public class UserRabbitMqProducer extends AbstractRabbitMqProducer {

    private RabbitTemplate rabbitTemplate;

    public UserRabbitMqProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.setConverter(new Jackson2JsonMessageConverter());
        this.setMessageCache(new UserMessageCache());
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void sendMessage(Message message, String routeKey) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(USER_EXCHANGE, routeKey, message, correlationData);
    }

    @Override
    public String getRouteKey(GenericMQEvent e) {
        return e.getClass().getSimpleName();
    }
}
