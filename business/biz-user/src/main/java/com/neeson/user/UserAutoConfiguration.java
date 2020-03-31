package com.neeson.user;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/31 22:01
 */
@Configuration
@ComponentScan
@EnableJpaRepositories
@EntityScan
public class UserAutoConfiguration {
}
