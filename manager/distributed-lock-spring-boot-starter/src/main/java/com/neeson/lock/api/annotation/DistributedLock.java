package com.neeson.lock.api.annotation;


import java.lang.annotation.*;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/28 20:08
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    String id();

    LockType lockType() default LockType.BLOCK;

}
