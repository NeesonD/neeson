package com.neeson.mq;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.neeson.common.constant.Symbol;
import com.neeson.mq.annotation.RabbitMqBinding;
import com.neeson.mq.constant.RabbitMqExchangeType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author neeson
 * publisher -> 正常(延迟) queue -> TTL -> dead.queue -> subscriber
 * Producer -> RoutingKey -> Exchange - BindingKey - Queue <- Consumer
 */
@Slf4j
@Component
public class RabbitMqBindingBeanPostProcessor implements BeanPostProcessor {


    @Value("${mq.common.exchange}")
    String defaultExchangeName;


    @Value("${mq.delay.exchange}")
    String deadExchangeName;


    @Autowired
    @Qualifier("rabbitAdmin")
    AmqpAdmin amqpAdmin;

    private static final String DEAD_SUFFIX = "_dead";
    private static final String JOINER = "#";



    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            RabbitMqBinding rabbitMqBinding = AnnotationUtils.findAnnotation(method, RabbitMqBinding.class);
            if (Objects.isNull(rabbitMqBinding)) {
                continue;
            }
            if (rabbitMqBinding.openDeadQueue()) {
                // 先绑定死信队列
                bindRabbitQueue(deadExchangeName, resolveDeadRouteKey(rabbitMqBinding, method), resolveDeadQueue(rabbitMqBinding, method), RabbitMqExchangeType.TOPIC);
                bindRabbitDelayQueue(
                        resolveExchange(rabbitMqBinding),
                        rabbitMqBinding.exchangeType(),
                        resolveQueue(rabbitMqBinding, method),
                        resolveRouteKeys(rabbitMqBinding,method),
                        resolveDeadRouteKey(rabbitMqBinding, method),
                        rabbitMqBinding.ttl()
                        );
            } else {
                bindRabbitQueue(
                        resolveExchange(rabbitMqBinding),
                        resolveRouteKey(rabbitMqBinding, method),
                        resolveQueue(rabbitMqBinding, method),
                        rabbitMqBinding.exchangeType());
            }
        }
        return bean;
    }

    /**
     * 获取 Exchange
     * @param rabbitMqBinding
     * @return
     */
    private String resolveExchange(RabbitMqBinding rabbitMqBinding) {
        if (StringUtils.isEmpty(rabbitMqBinding.exchange())) {
            return this.defaultExchangeName;
        }
        return rabbitMqBinding.exchange();
    }

    /**
     * 获取 RouteKey
     * @param rabbitMqBinding
     * @param method
     * @return
     */
    private String resolveRouteKey(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.routeKey())) {
            Assert.notNull(method.getParameterTypes(), method.getDeclaringClass().getSimpleName() + JOINER + method.getName() + "没有指定RouteKey");
            return method.getParameterTypes()[0].getSimpleName();
        }
        return rabbitMqBinding.routeKey();
    }

    /**
     * 获取 queue
     * @param rabbitMqBinding
     * @param method
     * @return
     */
    private String resolveQueue(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.queue())) {
            Assert.notEmpty(method.getParameterTypes(), method.getDeclaringClass().getSimpleName() + JOINER + method.getName() + "没有指定Queue");
            return method.getParameterTypes()[0].getSimpleName();
        }
        return rabbitMqBinding.queue();
    }


    public void bindRabbitDelayQueue(String exchangeName, RabbitMqExchangeType exchangeType, String delayQueueName, List<String> deadRouteKeyList, String deadRouteKey, int ttl) {
        Queue deadQueue = declareDelayQueue(delayQueueName, deadRouteKey, ttl);
        Exchange exchange = declareExchange(exchangeType, exchangeName);
        // 使死信队列的多个routeKey与deadQueue产生绑定关系
        deadRouteKeyList.forEach(routeKey -> declareRabbitQueueBinding(exchange, deadQueue, routeKey));
    }



    public void bindRabbitQueue(String exchangeName, String routeKey, String queueName, RabbitMqExchangeType exchangeType) {
        Queue queue = declareQueue(queueName);
        Exchange exchange = declareExchange(exchangeType, exchangeName);
        declareRabbitQueueBinding(exchange, queue, routeKey);
    }

    /**
     * 声明 exchange
     * @param exchangeType
     * @param exchangeName
     * @return
     */
    private Exchange declareExchange(RabbitMqExchangeType exchangeType, String exchangeName) {
        Exchange exchange;
        if (RabbitMqExchangeType.DIRECT.equals(exchangeType)) {
            exchange = new DirectExchange(exchangeName);
        } else if (RabbitMqExchangeType.TOPIC.equals(exchangeType)) {
            exchange = new TopicExchange(exchangeName);
        } else {
            exchange = new DirectExchange(defaultExchangeName);
        }
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    /**
     * 声明 Queue
     * @param queueName
     * @return
     */
    private Queue declareQueue(String queueName) {
        Queue queue = QueueBuilder.durable(queueName)
                .build();
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    /**
     * 声明 DelayQueue
     *
     * @param queueName
     * @param deadRouteKey
     * @param ttl
     */
    private Queue declareDelayQueue(String queueName, String deadRouteKey, int ttl) {
        Queue queue = QueueBuilder.durable(queueName)
                //设置死信交换机
                .withArgument("x-dead-letter-exchange", deadExchangeName)
                //超时时间
                .withArgument("x-message-ttl", ttl)
                //设置死信routingKey
                .withArgument("x-dead-letter-routing-key", deadRouteKey)
                .build();
        amqpAdmin.declareQueue(queue);
        return queue;
    }

    /**
     * 声明 BindingKey
     * @param exchange
     * @param queue
     * @param routeKey
     */
    private void declareRabbitQueueBinding(Exchange exchange, Queue queue, String routeKey) {
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routeKey).noargs());
    }





    /**
     * 解析 ttl
     *
     * @param rabbitMqBinding 标签
     * @return
     */
    private int resolveTTL(RabbitMqBinding rabbitMqBinding) {
        return rabbitMqBinding.ttl();
    }

    /**
     * 解析delayQueueName,直接使用 EventClass SimpleName + "_delay"
     *
     * @param rabbitMqBinding 标签
     * @param method          被标签的方法
     * @return
     */
    private String resolveDeadQueue(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.deadQueue())) {
            return rabbitMqBinding.deadQueue();
        } else {
            Assert.notEmpty(method.getParameterTypes(), "接受监听的对象 不能为空");
            return method.getParameterTypes()[0].getSimpleName() + DEAD_SUFFIX;
        }
    }





    private List<String> resolveRouteKeys(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.routeKey())) {
            Assert.notEmpty(method.getParameterTypes(), "接受监听的对象 不能为空");
            return CollectionUtil.newArrayList(method.getParameterTypes()[0].getSimpleName());
        }
        return Lists.newArrayList(StringUtils.split(rabbitMqBinding.deadRouteKey(), Symbol.COMMA));
    }


    private String resolveDeadRouteKey(RabbitMqBinding rabbitMqBinding, Method method) {
        if (StringUtils.isEmpty(rabbitMqBinding.deadRouteKey())) {
            return rabbitMqBinding.deadRouteKey();
        } else {
            Assert.notEmpty(method.getParameterTypes(), "接受监听的对象 不能为空");
            return method.getParameterTypes()[0].getSimpleName() + DEAD_SUFFIX;
        }
    }


}
