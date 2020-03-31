package com.neeson.common.user.event;

import com.neeson.common.event.GenericApplicationEvent;
import com.neeson.common.user.event.cmd.UserAddPostEventCmd;

public class UserAddPostEvent extends GenericApplicationEvent<UserAddPostEventCmd> {

    public UserAddPostEvent(UserAddPostEventCmd source) {
        super(source);
    }
}
