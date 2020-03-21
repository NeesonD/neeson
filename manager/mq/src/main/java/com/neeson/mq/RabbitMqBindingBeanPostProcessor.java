package com.neeson.mq;

import com.neeson.mq.annotation.RabbitMqBinding;
import com.neeson.mq.constant.RabbitMqExchangeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import static com.neeson.mq.utils.AbstractRabbitMqBindingParse.*;

/**
 * @author neeson
 * publisher -> 正常(延迟) queue -> TTL -> dead.queue -> subscriber
 * Producer -> RoutingKey -> Exchange - BindingKey - Queue <- Consumer
 */
@Slf4j
@Component
public class RabbitMqBindingBeanPostProcessor implements BeanPostProcessor {


    @Autowired
    private AmqpAdmin amqpAdmin;


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
                bindRabbitQueue(
                        resolveDeadExchange(rabbitMqBinding),
                        resolveDeadRouteKey(rabbitMqBinding, method),
                        resolveDeadQueue(rabbitMqBinding, method),
                        RabbitMqExchangeType.TOPIC);
                // 再绑定延迟队列
                bindRabbitDelayQueue(
                        resolveExchange(rabbitMqBinding),
                        rabbitMqBinding.exchangeType(),
                        resolveDeadExchange(rabbitMqBinding),
                        resolveQueue(rabbitMqBinding, method),
                        resolveRouteKeys(rabbitMqBinding, method),
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


    public void bindRabbitDelayQueue(String exchangeName, RabbitMqExchangeType exchangeType, String deadExchangeName, String delayQueueName, List<String> deadRouteKeyList, String deadRouteKey, int ttl) {
        Queue deadQueue = declareDelayQueue(delayQueueName, deadExchangeName, deadRouteKey, ttl);
        Exchange exchange = declareExchange(exchangeType, exchangeName);
        deadRouteKeyList.forEach(routeKey -> declareRabbitQueueBinding(exchange, deadQueue, routeKey));
    }

    public void bindRabbitQueue(String exchangeName, String routeKey, String queueName, RabbitMqExchangeType exchangeType) {
        Queue queue = declareQueue(queueName);
        Exchange exchange = declareExchange(exchangeType, exchangeName);
        declareRabbitQueueBinding(exchange, queue, routeKey);
    }

    /**
     * 声明 exchange
     *
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
            exchange = new DirectExchange(exchangeName);
        }
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    /**
     * 声明 Queue
     *
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
    private Queue declareDelayQueue(String queueName, String deadExchangeName, String deadRouteKey, int ttl) {
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
     *
     * @param exchange
     * @param queue
     * @param routeKey
     */
    private void declareRabbitQueueBinding(Exchange exchange, Queue queue, String routeKey) {
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routeKey).noargs());
    }
}
