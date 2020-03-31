package com.neeson.account.biz.user.mq;

import com.neeson.account.biz.user.event.UserAddPostMqEvent;
import com.neeson.mq.annotation.RabbitMqBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.neeson.account.mq.RabbitMqConstant.USER_EXCHANGE;
import static com.neeson.common.constant.LogConstant.LOG_PRE;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/31 19:57
 */
@Slf4j
@Component
public class UserMqListener {

    @RabbitMqBinding(exchange = USER_EXCHANGE)
    @RabbitListener(queues = "UserAddPostMqEvent")
    public void listenUserAddPostMqEvent(UserAddPostMqEvent event, MessageProperties messageProperties) {
        Long userId = event.getUserId();
        log.error(LOG_PRE + userId);
    }

}
