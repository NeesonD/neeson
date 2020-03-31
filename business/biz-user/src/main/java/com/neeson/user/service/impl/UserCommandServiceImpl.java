package com.neeson.user.service.impl;

import com.neeson.common.user.event.UserAddPostEvent;
import com.neeson.common.user.event.cmd.UserAddPostEventCmd;
import com.neeson.user.domain.User;
import com.neeson.user.rep.UserRepository;
import com.neeson.user.service.IUserCommandService;
import com.neeson.user.service.cmd.UserAddCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author neeson
 */
@Service
public class UserCommandServiceImpl implements IUserCommandService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationEventPublisher publisher;


    @Override
    public void add(UserAddCmd cmd) {
        User user = cmd.toDomain();
        userRepository.save(cmd.toDomain());
        publisher.publishEvent(new UserAddPostEvent(UserAddPostEventCmd.of(user.getId())));
    }
}
