package com.neeson.account.biz.user.event.cmd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author neeson
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserAddPostMqEventCmd {

    private Long userId;

}
