package com.neeson.account.mq;

import com.neeson.mq.GenericMQEvent;
import com.neeson.mq.cache.IMessageCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.neeson.common.constant.LogConstant.LOG_PRE;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 19:29
 */
@Slf4j
@Component
public class UserMessageCache implements IMessageCache {
    @Override
    public void add(GenericMQEvent event) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + LOG_PRE + event.getMessageId());
    }

    @Override
    public void sendSuccess(String id) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + LOG_PRE + id);
    }

    @Override
    public void sendFailed(String id, String cause) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + LOG_PRE + id + cause);
    }

    @Override
    public void consumptionFailure(String id) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + LOG_PRE + id);
    }

    @Override
    public void complete(String id) {
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + LOG_PRE + id);
    }
}
