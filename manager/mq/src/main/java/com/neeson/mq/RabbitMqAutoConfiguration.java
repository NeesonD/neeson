package com.neeson.mq;

import com.neeson.mq.bean.RabbitMqBindingBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 21:17
 */
@Configuration
@Import(RabbitMqBindingBeanPostProcessor.class)
public class RabbitMqAutoConfiguration {
}
