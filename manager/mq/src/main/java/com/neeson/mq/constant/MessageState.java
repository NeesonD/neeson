package com.neeson.mq.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息状态
 * @author neeson
 */
@Getter
@AllArgsConstructor
public enum MessageState {

    INIT("INIT","新增一条消息"),
    SEND_SUCCESS("SEND_SUCCESS","消息发送成功"),
    SEND_FAILED("SEND_FAILED","消息发送失败"),
    CONSUMPTION_FAILURE("CONSUMPTION_FAILURE","消息消费失败"),
    COMPLETED("COMPLETED","消息消费成功")
    ;

    final String value;

    final String message;

}
