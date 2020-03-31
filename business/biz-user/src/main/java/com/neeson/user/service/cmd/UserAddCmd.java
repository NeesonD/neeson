package com.neeson.user.service.cmd;

import com.neeson.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

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
        User user = new User();
        user.setId(getUserId());
        user.setPhone(phone);
        user.setPassword(password);
        return user;
    }

    public Long getUserId() {
        return new Random().nextLong();
    }
}
