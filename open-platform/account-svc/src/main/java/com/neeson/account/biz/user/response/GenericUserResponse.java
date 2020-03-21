package com.neeson.account.biz.user.response;

import com.neeson.account.biz.user.response.dto.UserDto;
import com.neeson.common.api.BaseResponse;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericUserResponse extends BaseResponse {

    private UserDto userDto;

}
