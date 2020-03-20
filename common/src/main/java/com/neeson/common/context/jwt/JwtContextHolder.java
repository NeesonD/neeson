package com.neeson.common.context.jwt;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.neeson.common.context.ContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Create on 2019-09-23
 *
 * @author DaiLe
 */
@Component
public class JwtContextHolder implements ContextHolder<JwtContext> {


    private static TransmittableThreadLocal<JwtContext> jwtContextTransmittableThreadLocal = new TransmittableThreadLocal<JwtContext>();


    public void set(JwtContext jwtContext) {
        Assert.notNull(jwtContext, "jwt 不能为空");
        jwtContextTransmittableThreadLocal.set(jwtContext);
    }

    public JwtContext get() {
        return jwtContextTransmittableThreadLocal.get();
    }

    public void clear() {
        jwtContextTransmittableThreadLocal.remove();
    }


}
