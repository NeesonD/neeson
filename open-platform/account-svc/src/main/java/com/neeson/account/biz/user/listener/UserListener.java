package com.neeson.account.biz.user.listener;

import com.neeson.account.biz.user.event.UserAddPostEvent;
import com.neeson.account.biz.user.event.UserAddPostMqEvent;
import com.neeson.account.biz.user.event.cmd.UserAddPostEventCmd;
import com.neeson.account.biz.user.event.cmd.UserAddPostMqEventCmd;
import com.neeson.mq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author neeson
 */
@Component
public class UserListener {

    @Autowired
    private MqProducer mqProducer;

    /**
     * @param event
     */
    @EventListener
    public void sendMessage(UserAddPostEvent event) {

    }


    /**
     * 将发 q 的行为滞后，避免发 q 成功，事务失败
     * 使用 BEFORE_COMMIT 是为了在同一个事务里面保存消息。
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void syncUser(UserAddPostEvent event) {
        UserAddPostEventCmd source = event.getSource();
        mqProducer.publishEvent(new UserAddPostMqEvent(UserAddPostMqEventCmd.of(source.getUserId())));
    }

}
