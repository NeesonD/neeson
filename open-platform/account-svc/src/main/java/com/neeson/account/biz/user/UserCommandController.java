package com.neeson.account.biz.user;

import com.neeson.account.biz.user.request.UserAddRequest;
import com.neeson.account.biz.user.service.IUserCommandService;
import com.neeson.account.biz.user.service.cmd.UserAddCmd;
import com.neeson.common.api.BaseResponse;
import com.neeson.zk.service.DistributedLockByCurator;
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
    @Autowired
    private DistributedLockByCurator lockByCurator;

    /**
     * 新增学员
     * @param request
     */
    @ApiOperation(value = "新增学员")
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(@RequestBody UserAddRequest request) {
        try {
            lockByCurator.acquireDistributedLock(request.getPhone());
            userCommandService.add(UserAddCmd.of(request.getPhone(),request.getPassword()));
        } finally {
            lockByCurator.releaseDistributedLock(request.getPhone());
        }
        return new BaseResponse();
    }
}
