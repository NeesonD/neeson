package com.neeson.common.autoconfigure;

import com.neeson.common.http.RestTemplateConfiguration;
import com.neeson.common.http.interceptor.JwtHeaderRequestInterceptor;
import com.neeson.common.http.interceptor.LogRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 21:47
 */
@Configuration
@Import(RestTemplateConfiguration.class)
public class HttpAutoConfiguration {

    @Configuration
    @Import({JwtHeaderRequestInterceptor.class, LogRequestInterceptor.class})
    public static class ClientHttpRequestInterceptorConfiguration {

    }

}
