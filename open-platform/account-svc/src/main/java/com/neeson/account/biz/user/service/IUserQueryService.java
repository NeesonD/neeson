package com.neeson.account.biz.user.service;

import com.neeson.account.biz.user.domain.User;
import com.neeson.account.biz.user.service.cmd.UserAddCmd;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author neeson
 */
public interface IUserQueryService {

    @Retryable(value = {SQLException.class}, maxAttempts = 2, backoff = @Backoff(delay = 5000))
    User get(Long userId);

}
