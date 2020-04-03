package com.neeson.rpc.support;

import java.lang.annotation.*;

/**
 * Create on 2020-04-03
 *
 * @author Administrator
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceName {

    String value() default "";

}
