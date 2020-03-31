package com.neeson.common.autoconfigure;

import com.neeson.common.context.jwt.JwtContextHolder;
import com.neeson.common.context.trace.TraceContextHolder;
import com.neeson.common.filter.JwtContextFilter;
import com.neeson.common.filter.TracePostFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 21:42
 */
@Configuration
@Import({TracePostFilter.class, JwtContextFilter.class})
public class FilterAutoConfiguration {


    @Configuration
    @Import({JwtContextHolder.class, TraceContextHolder.class})
    public static class ContextHolderConfiguration {

    }

}
