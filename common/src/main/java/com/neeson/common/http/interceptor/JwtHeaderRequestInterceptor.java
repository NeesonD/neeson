package com.neeson.common.http.interceptor;


import com.neeson.common.auth.AuthConstant;
import com.neeson.common.context.jwt.JwtContext;
import com.neeson.common.context.jwt.JwtContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Create on 2019-09-23

 * 设置请求头，保证服务调用上下文的传输
 * @author DaiLe
 */
@Slf4j
@Order(1)
@Component
public class JwtHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private JwtContextHolder jwtContextHolder;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        JwtContext jwtContext = jwtContextHolder.get();
        request.getHeaders().set(AuthConstant.AUTHORIZATION_HEADER, jwtContext.getToken());
        return execution.execute(request, body);
    }
}
