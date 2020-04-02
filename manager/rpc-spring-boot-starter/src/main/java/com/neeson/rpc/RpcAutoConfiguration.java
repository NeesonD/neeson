package com.neeson.rpc;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/2 22:04
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(RpcProperties.class)
public class RpcAutoConfiguration {
}
