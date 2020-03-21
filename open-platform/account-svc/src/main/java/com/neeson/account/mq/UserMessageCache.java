package com.neeson.account.mq;

import com.neeson.mq.GenericMQEvent;
import com.neeson.mq.cache.IMessageCache;
import org.springframework.stereotype.Component;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 19:29
 */
@Component
public class UserMessageCache implements IMessageCache {
    @Override
    public void add(GenericMQEvent event) {

    }

    @Override
    public void sendSuccess(String id) {

    }

    @Override
    public void sendFailed(String id, String cause) {

    }

    @Override
    public void consumptionFailure(String id) {

    }

    @Override
    public void complete(String id) {

    }
}
