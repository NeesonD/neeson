package com.neeson.account.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author daile
 * @version 1.0
 * @date 2020/4/16 21:12
 */
@ConfigurationProperties("kafka.topic")
public class KafkaTopicProperties {

    private String groupId;
    private String[] topicName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String[] getTopicName() {
        return topicName;
    }

    public void setTopicName(String[] topicName) {
        this.topicName = topicName;
    }

}
