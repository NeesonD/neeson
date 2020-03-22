package com.neeson.account.biz.user.event;

import com.neeson.mq.GenericMQEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author neeson
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserAddPostMqEvent extends GenericMQEvent {
    private Long userId;
}
