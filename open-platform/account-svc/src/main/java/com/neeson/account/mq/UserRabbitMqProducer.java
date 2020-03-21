package com.neeson.account.mq;

import com.neeson.mq.AbstractRabbitMqProducer;
import com.neeson.mq.GenericMQEvent;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * @author neeson
 */
@Component
public class UserRabbitMqProducer extends AbstractRabbitMqProducer {

    @Override
    public void sendMessage(Message message, String routeKey) {
        rabbitTemplate.convertAndSend("", routeKey, message);
    }

    @Override
    public String getRouteKey(GenericMQEvent e) {
        return e.getClass().getSimpleName();
    }
}
