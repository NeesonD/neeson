package com.neeson.account.biz.user;

import com.neeson.account.biz.user.request.UserAddRequest;
import com.neeson.account.biz.user.service.IUserCommandService;
import com.neeson.account.biz.user.service.cmd.UserAddCmd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author neeson
 */
@Api(tags = "用户命令")
@RestController
@RequestMapping("/web/user")
public class UserCommandController {

    @Autowired
    private IUserCommandService userCommandService;

    /**
     * 新增学员
     * @param request
     */
    @ApiOperation(value = "新增学员")
    @PostMapping("/add")
    public void add(@RequestBody UserAddRequest request) {
        userCommandService.add(UserAddCmd.of(request.getPhone(),request.getPassword()));
    }
}
