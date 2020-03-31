package com.neeson.user.service;

import com.neeson.user.domain.User;

/**
 * @author neeson
 */
public interface IUserQueryService {

    User get(Long userId);

}
