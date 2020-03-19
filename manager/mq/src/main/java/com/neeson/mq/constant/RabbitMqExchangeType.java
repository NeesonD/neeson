package com.neeson.mq.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author neeson
 */
@Getter
@AllArgsConstructor
public enum RabbitMqExchangeType {
    TOPIC("TOPIC", "TOPIC"),
    DIRECT("DIRECT", "直接"),
    ;

    final String value;

    final String name;
}
