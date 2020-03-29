package com.neeson.lock.api.aop;

import com.neeson.common.utils.SpelUtils;
import com.neeson.lock.api.LockInfo;
import com.neeson.lock.api.annotation.DistributedLock;
import com.neeson.lock.api.annotation.IDistributedLock;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.util.SimpleIdGenerator;

import java.lang.reflect.Method;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/28 20:49
 */
@Slf4j
@Aspect
@Import(SimpleIdGenerator.class)
public class DistributedLockAspect {

    @Autowired
    private IDistributedLock lock;
    @Autowired
    private SimpleIdGenerator simpleIdGenerator;


    @Pointcut("@annotation(distributedLock)")
    public void pointCut(DistributedLock distributedLock) {
    }

    @SneakyThrows
    @Around(value = "pointCut(distributedLock)", argNames = "point,distributedLock")
    public Object around(ProceedingJoinPoint point, DistributedLock distributedLock) {
        String key = getLockKey(point, distributedLock);
        LockInfo lockInfo = new LockInfo(key, simpleIdGenerator.generateId().toString());
        try {
            lock.lock(lockInfo);
            return point.proceed();
        } finally {
            lock.unLock(lockInfo);
        }
    }



    private String getLockKey(JoinPoint point, DistributedLock distributedLock) {
        String id = distributedLock.id();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Object[] arguments = point.getArgs();
        return SpelUtils.parse(id, method, arguments);
    }
}
