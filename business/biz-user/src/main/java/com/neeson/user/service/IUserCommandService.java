package com.neeson.user.service;

import com.neeson.user.service.cmd.UserAddCmd;
import org.springframework.stereotype.Service;

/**
 * @author neeson
 */
public interface IUserCommandService {

    /**
     * 添加学生
     * @param cmd
     */
    void add(UserAddCmd cmd);

}
