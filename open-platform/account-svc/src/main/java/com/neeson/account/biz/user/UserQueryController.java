package com.neeson.account.biz.user;

import com.neeson.account.biz.user.domain.User;
import com.neeson.account.biz.user.response.GenericUserResponse;
import com.neeson.account.biz.user.response.dto.UserDto;
import com.neeson.account.biz.user.service.IUserQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author neeson
 */
@Api(tags = "用户查询")
@RestController
@RequestMapping("/web/user")
public class UserQueryController {

    @Autowired
    private IUserQueryService userQueryService;

    /**
     * 获取学员
     * @param id
     */
    @ApiOperation(value = "获取学员")
    @GetMapping("/get")
    public GenericUserResponse get(Long id) {
        User user = userQueryService.get(id);
        return new GenericUserResponse(UserDto.of(user.getPhone()));
    }
}
