package com.neeson.user.rep;

import com.neeson.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author neeson
 */
public interface UserRepository extends JpaRepository<User, Long> {
}