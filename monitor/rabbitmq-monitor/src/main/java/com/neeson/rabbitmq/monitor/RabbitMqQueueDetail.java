package com.neeson.rabbitmq.monitor;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/23 22:56
 */
public class RabbitMqQueueDetail {

    /**
     * 队列名称
     */
    private String queueName;

    /**
     * 队列数量
     */
    private int queueNum;

    /**
     * 活动消费者数量
     */
    private int activeConsumerCount;

}
