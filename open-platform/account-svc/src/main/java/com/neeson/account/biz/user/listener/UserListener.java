package com.neeson.account.biz.user.listener;

import com.neeson.account.biz.user.domain.User;
import com.neeson.account.biz.user.event.UserAddPostEvent;
import com.neeson.account.biz.user.event.UserAddPostMqEvent;
import com.neeson.account.biz.user.event.cmd.UserAddPostEventCmd;
import com.neeson.account.biz.user.event.cmd.UserAddPostMqEventCmd;
import com.neeson.account.biz.user.rep.UserRepository;
import com.neeson.account.mq.UserMessageCache;
import com.neeson.mq.MqProducer;
import com.neeson.mq.annotation.RabbitMqBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

import static com.neeson.account.mq.RabbitMqConstant.USER_EXCHANGE;
import static com.neeson.common.constant.LogConstant.LOG_PRE;

/**
 * @author neeson
 */
@Slf4j
@Component
public class UserListener {

    @Autowired
    private MqProducer mqProducer;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMessageCache messageCache;

    /**
     * @param event
     */
    @EventListener
    public void sendMessage(UserAddPostEvent event) {
        UserAddPostEventCmd source = event.getSource();
        log.info(LOG_PRE + this.getClass().getSimpleName() + source.toString());
    }


    /**
     * 将发 q 的行为滞后，避免发 q 成功，事务失败
     * 使用 BEFORE_COMMIT 是为了在同一个事务里面保存消息。
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void syncUser(UserAddPostEvent event) {
        UserAddPostEventCmd source = event.getSource();
        mqProducer.publishEvent(UserAddPostMqEvent.of(source.getUserId()));
    }

    @RabbitMqBinding(exchange = USER_EXCHANGE)
    @RabbitListener(queues = "UserAddPostMqEvent")
    public void listenUserAddPostMqEvent(UserAddPostMqEvent event, MessageProperties messageProperties) {
        Long userId = event.getUserId();
        log.error(LOG_PRE + event.toString());
        log.error(LOG_PRE + messageProperties.toString());
        messageCache.complete(messageProperties.getMessageId());
    }

}
