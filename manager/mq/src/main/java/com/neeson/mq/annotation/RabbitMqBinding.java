package com.neeson.mq.annotation;

import com.neeson.mq.constant.RabbitMqExchangeType;

import java.lang.annotation.*;

/**
 * @author neeson
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RabbitMqBinding {

    /**
     * 不指定的时候，默认使用 Event ClassName
     *
     * @return
     */
    String queue() default "";

    /**
     * 不指定的时候，使用默认值
     *
     * @return
     */
    String exchange() default "";

    /**
     * 不指定的时候，默认使用 Event ClassName
     *
     * @return
     */
    String routeKey() default "";

    /**
     * Exchange 类型
     *
     * @return
     */
    RabbitMqExchangeType exchangeType() default RabbitMqExchangeType.TOPIC;

    /**
     * 是否开启死信队列，开启的时候，listener 监听 deadRouteKey
     *
     * @return
     */
    boolean openDeadQueue() default false;

    /**
     * 死信队列的queueName；如果是空， 使用 Event ClassName + "_dead"
     *
     * @return
     */
    String deadQueue() default "";

    /**
     * 死信队列的routeKey；如果是空， 使用 Event ClassName + "_dead"
     *
     * @return
     */
    String deadRouteKey() default "";

    /**
     * 延迟队列时间；单位为ms，如果是空，则为5000
     *
     * @return
     */
    int ttl() default 5000;

}
