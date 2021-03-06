package com.neeson.account.biz.user;

import com.neeson.account.biz.user.request.UserAddRequest;
import com.neeson.common.aop.ApiIdempotent;
import com.neeson.user.service.IUserCommandService;
import com.neeson.user.service.cmd.UserAddCmd;
import com.neeson.common.api.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
     *
     * @param request
     */
    @ApiOperation(value = "新增学员")
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ApiIdempotent(id = "")
    public BaseResponse add(@RequestBody UserAddRequest request) {
        userCommandService.add(UserAddCmd.of(request.getPhone(), request.getPassword()));
        return new BaseResponse();
    }
}
