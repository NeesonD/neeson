package com.neeson.common.autoconfigure;

import com.neeson.common.http.RestTemplateConfiguration;
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



}
