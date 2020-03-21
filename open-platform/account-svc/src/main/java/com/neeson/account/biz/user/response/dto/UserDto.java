package com.neeson.account.biz.user.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author neeson
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserDto {

    private String phone;

}
