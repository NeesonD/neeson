package com.neeson.mq.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.neeson.common.constant.Symbol;
import com.neeson.mq.annotation.RabbitMqBinding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Create on 2020-03-20
 *
 * @author Administrator
 */
public abstract class AbstractRabbitMqBindingParse {

    private static final String DEAD_SUFFIX = "_dead";


    /**
     * 获取 Exchange
     *
     * @param rabbitMqBinding
     * @return
     */
    public static String resolveExchange(RabbitMqBinding rabbitMqBinding, String defaultExchangeName) {
        if (StringUtils.isEmpty(rabbitMqBinding.exchange())) {
            return defaultExchangeName;
        }
        return rabbitMqBinding.exchange();
    }

    /**
     * 获取 RouteKey
     *
     * @param rabbitMqBinding
     * @param method
     * @return
     */
    public static String resolveRouteKey(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.routeKey())) {
            Assert.notNull(method.getParameterTypes(), method.getDeclaringClass().getSimpleName() + Symbol.AND + method.getName() + "没有指定RouteKey");
            return method.getParameterTypes()[0].getSimpleName();
        }
        return rabbitMqBinding.routeKey();
    }

    /**
     * 获取 queue
     *
     * @param rabbitMqBinding
     * @param method
     * @return
     */
    public static String resolveQueue(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.queue())) {
            Assert.notEmpty(method.getParameterTypes(), method.getDeclaringClass().getSimpleName() + Symbol.AND + method.getName() + "没有指定Queue");
            return method.getParameterTypes()[0].getSimpleName();
        }
        return rabbitMqBinding.queue();
    }

    public static String resolveDeadQueue(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.deadQueue())) {
            Assert.notEmpty(method.getParameterTypes(), method.getDeclaringClass().getSimpleName() + Symbol.AND + method.getName() + "没有指定DeadQueue");
            return method.getParameterTypes()[0].getSimpleName() + DEAD_SUFFIX;
        }
        return rabbitMqBinding.deadQueue();
    }


    public static String resolveDeadRouteKey(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.deadRouteKey())) {
            Assert.notEmpty(method.getParameterTypes(), method.getDeclaringClass().getSimpleName() + Symbol.AND + method.getName() + "没有指定DeadRouteKey");
            return method.getParameterTypes()[0].getSimpleName() + DEAD_SUFFIX;
        }
        return rabbitMqBinding.deadRouteKey();
    }

    public static List<String> resolveRouteKeys(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.routeKey())) {
            Assert.notEmpty(method.getParameterTypes(), method.getDeclaringClass().getSimpleName() + Symbol.AND + method.getName() + "没有指定RouteKeys");
            return CollectionUtil.newArrayList(method.getParameterTypes()[0].getSimpleName());
        }
        return Lists.newArrayList(StringUtils.split(rabbitMqBinding.deadRouteKey(), Symbol.COMMA));
    }

}
