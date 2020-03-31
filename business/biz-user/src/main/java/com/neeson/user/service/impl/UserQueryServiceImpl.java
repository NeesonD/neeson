package com.neeson.user.service.impl;

import com.neeson.user.domain.User;
import com.neeson.user.rep.UserRepository;
import com.neeson.user.service.IUserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author neeson
 */
@Service
public class UserQueryServiceImpl implements IUserQueryService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User get(Long userId) {
         return userRepository.findById(userId).orElse(new User());
    }
}
