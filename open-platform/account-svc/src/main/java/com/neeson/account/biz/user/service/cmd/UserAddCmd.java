package com.neeson.account.biz.user.service.cmd;

import com.neeson.account.biz.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author neeson
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserAddCmd {

    private String phone;

    private String password;

    public User toDomain() {
        return null;
    }
}
