package com.neeson.account.biz.user;

import com.neeson.account.biz.user.domain.User;
import com.neeson.account.biz.user.response.GenericUserResponse;
import com.neeson.account.biz.user.response.dto.UserDto;
import com.neeson.account.biz.user.service.IUserQueryService;
import com.neeson.common.api.BaseResponse;
import com.neeson.zk.service.ZkDistributedLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.neeson.common.constant.LogConstant.LOG_PRE;

/**
 * @author neeson
 */
@Api(tags = "用户查询")
@Slf4j
@RestController
@RequestMapping("/web/user")
public class UserQueryController {

    @Autowired
    private IUserQueryService userQueryService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ZkDistributedLock lockByCurator;

    /**
     * 获取学员
     * @param id
     */
    @ApiOperation(value = "获取学员")
    @GetMapping("/get")
    public GenericUserResponse get(Long id) {
        stringRedisTemplate.opsForValue().set(String.valueOf(id), UUID.randomUUID().toString());
        String userInfo = stringRedisTemplate.opsForValue().get(String.valueOf(id));
        log.error(LOG_PRE + userInfo);
        User user = userQueryService.get(id);
        return new GenericUserResponse(UserDto.of(user.getPhone()));
    }

    @GetMapping("testZkLock")
    public BaseResponse testZkLock() throws InterruptedException {
        lockByCurator.acquireDistributedLock("neeson");
        log.info("");
        Thread.sleep(20000L);
        lockByCurator.releaseDistributedLock("neeson");
        return new BaseResponse();
    }
}
