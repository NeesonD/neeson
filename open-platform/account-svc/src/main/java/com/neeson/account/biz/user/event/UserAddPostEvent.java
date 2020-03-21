package com.neeson.account.biz.user.event;

import com.neeson.account.biz.user.event.cmd.UserAddPostEventCmd;
import com.neeson.common.event.GenericApplicationEvent;

public class UserAddPostEvent extends GenericApplicationEvent<UserAddPostEventCmd> {

    public UserAddPostEvent(UserAddPostEventCmd source) {
        super(source);
    }
}
