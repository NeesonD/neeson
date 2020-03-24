package com.neeson.common.aop;

import java.lang.annotation.*;

/**
 * 幂等
 * @author neeson
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiIdempotent {

    /**
     * 防重 id
     * @return
     */
    String id();

    /**
     * 锁时间
     * @return
     */
    int lockTime() default 10;

}
