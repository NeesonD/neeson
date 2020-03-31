package com.neeson.account.biz.user.listener;

import com.neeson.account.biz.user.event.UserAddPostMqEvent;
import com.neeson.account.mq.UserMessageCache;
import com.neeson.common.user.event.UserAddPostEvent;
import com.neeson.common.user.event.cmd.UserAddPostEventCmd;
import com.neeson.mq.MqProducer;
import com.neeson.mq.annotation.RabbitMqBinding;
import com.neeson.user.rep.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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
    }


    /**
     * 将发 q 的行为滞后，避免发 q 成功，事务失败
     * 使用 BEFORE_COMMIT 是为了在同一个事务里面保存消息。
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncUser(UserAddPostEvent event) {
        UserAddPostEventCmd source = event.getSource();
        mqProducer.publishEvent(UserAddPostMqEvent.of(source.getUserId()));
    }



}
