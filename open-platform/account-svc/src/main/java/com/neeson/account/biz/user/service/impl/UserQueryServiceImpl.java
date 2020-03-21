package com.neeson.account.biz.user.service.impl;

import com.neeson.account.biz.user.domain.User;
import com.neeson.account.biz.user.rep.UserRepository;
import com.neeson.account.biz.user.service.IUserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author neeson
 */
@Service
public class UserQueryServiceImpl implements IUserQueryService {

    @Autowired
    private UserRepository userRepository;

    public User get(Long userId) {
         return userRepository.findById(userId).orElse(new User());
    }
}
