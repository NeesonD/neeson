package com.neeson.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/31 23:45
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ConfigServerEnvironmentPostProcessor.class)
public @interface EnableConfigServer {
}
