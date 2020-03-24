package com.neeson.mq.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Arrays;

import static com.neeson.common.constant.LogConstant.LOG_PRE;

/**
 * Create on 2020-03-24
 *
 * @author Administrator
 */
@Slf4j
@Aspect
public class RabbitMqListenerAspect {


    @Pointcut(value = "@annotation(rabbitListener)")
    public void pointCut(RabbitListener rabbitListener) {
    }


    @AfterReturning(pointcut = "pointCut(rabbitListener)")
    public void afterReturningAdvice(JoinPoint jp, RabbitListener rabbitListener) {
        log.error(LOG_PRE + Arrays.toString(rabbitListener.queues()));
    }

    @AfterThrowing(pointcut = "pointCut(rabbitListener)", throwing = "error")
    public void afterThrowingAdvice(Throwable error,RabbitListener rabbitListener) {
        log.error(LOG_PRE + error.getMessage());
    }

}
