package com.neeson.common.jwt;


import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Create on 2019-09-23
 *
 * @author DaiLe
 */
@Component
public class JwtContextService {


    private static TransmittableThreadLocal<JwtContext> loginJwtContextThreadLocal = new TransmittableThreadLocal<>();

    /**
     * 设置当前的JWT
     *
     * @param jwtContext
     */
    public void setJwtContext(JwtContext jwtContext) {
        Assert.notNull(jwtContext, "jwt 不能为空");
        loginJwtContextThreadLocal.set(jwtContext);
    }

    /**
     * 清除JWT
     */
    public void clearWeChatLoginContext() {
        loginJwtContextThreadLocal.remove();
    }

    /**
     * 获取当前线程中的JWT
     *
     * @return
     */
    public JwtContext getJwtContext() {
        return loginJwtContextThreadLocal.get();
    }

}
