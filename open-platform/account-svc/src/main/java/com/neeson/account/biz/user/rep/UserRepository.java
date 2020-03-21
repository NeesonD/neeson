package com.neeson.account.biz.user.rep;

import com.neeson.account.biz.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author neeson
 */
public interface UserRepository extends JpaRepository<User, Long> {
}