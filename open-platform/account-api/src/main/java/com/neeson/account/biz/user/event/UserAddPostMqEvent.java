package com.neeson.account.biz.user.event;

import com.neeson.account.biz.user.event.cmd.UserAddPostMqEventCmd;
import com.neeson.mq.GenericMQEvent;

/**
 * @author neeson
 */
public class UserAddPostMqEvent extends GenericMQEvent<UserAddPostMqEventCmd> {
    public UserAddPostMqEvent(UserAddPostMqEventCmd eventSource) {
        super(eventSource);
    }
}
