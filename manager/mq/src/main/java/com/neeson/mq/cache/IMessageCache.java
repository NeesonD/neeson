package com.neeson.mq.cache;

import com.neeson.mq.GenericMQEvent;

/**
 * @author neeson
 */
public interface IMessageCache {

    /**
     * 发送消息的时候，将消息存储到数据库
     * 这里如果用的是 MYSQL 存储，则可以保证事务
     * @param event
     */
    void add(GenericMQEvent event);

    /**
     * 发送成功处理
     * @param id
     */
    void sendSuccess(String id);

    /**
     * 发送失败处理
     * @param id
     * @param cause
     */
    void sendFailed(String id,String cause);

    /**
     * 消费失败
     * @param id
     */
    void consumptionFailure(String id);

    /**
     * 完成
     * @param id
     */
    void complete(String id);


}
