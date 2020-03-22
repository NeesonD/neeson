package com.neeson.common.aop;

import cn.hutool.core.util.StrUtil;
import com.neeson.common.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

import static com.neeson.common.constant.HttpConstant.IDEMPOTENT_ID;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/22 14:43
 */
@Slf4j
@Aspect
@Component
public class ApiIdempotentAspect {

    /**
     * TODO，后续改成 redis
     */
    private static ConcurrentHashMap<String, String> idCache = new ConcurrentHashMap<>();

    @Pointcut("@annotation(apiIdempotent)")
    public void pointCut(ApiIdempotent apiIdempotent) {
    }


    @Before(value = "pointCut(apiIdempotent)", argNames = "joinPoint,apiIdempotent")
    public void before(JoinPoint joinPoint, ApiIdempotent apiIdempotent) {

        String idempotentId = getIdempotentId(apiIdempotent);

        String value = idCache.putIfAbsent(idempotentId, "IDEMPOTENT_ID");

        Assert.isNull(value,"请求重复，请稍后再试");

    }


    private String getIdempotentId(ApiIdempotent apiIdempotent) {
        if (StrUtil.isNotEmpty(apiIdempotent.id())) {
            return apiIdempotent.id();
        }
        HttpServletRequest request = RequestUtils.getHttpServletRequest();
        String idempotentId = request.getHeader(IDEMPOTENT_ID);
        Assert.hasLength(idempotentId, "没有防重 id");
        return idempotentId;
    }

}
