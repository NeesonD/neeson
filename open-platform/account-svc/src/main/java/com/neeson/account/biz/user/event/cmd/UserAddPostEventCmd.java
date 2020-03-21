package com.neeson.account.biz.user.event.cmd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserAddPostEventCmd {

    private Long userId;

}
