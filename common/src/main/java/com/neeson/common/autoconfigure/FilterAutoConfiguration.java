package com.neeson.common.autoconfigure;

import com.neeson.common.filter.JwtContextFilter;
import com.neeson.common.filter.TracePostFilter;
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


}
