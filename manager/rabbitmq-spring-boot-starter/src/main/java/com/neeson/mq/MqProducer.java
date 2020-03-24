package com.neeson.mq;

/**
 * Create on 2020-03-20
 *
 * @author Administrator
 */
public interface MqProducer {

    /**
     * 发送事件
     * @param event
     */
    void publishEvent(GenericMQEvent event);

}
