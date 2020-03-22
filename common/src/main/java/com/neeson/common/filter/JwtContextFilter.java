package com.neeson.common.filter;

import com.neeson.common.auth.AuthConstant;
import com.neeson.common.constant.FilterOrderConstant;
import com.neeson.common.context.jwt.JwtContext;
import com.neeson.common.context.jwt.JwtContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import static com.neeson.common.constant.LogConstant.LOG_PRE;


/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 22:08
 */
@Slf4j
@Order(value = FilterOrderConstant.CONTEXT)
public class JwtContextFilter implements Filter {

    @Autowired
    private JwtContextHolder jwtContextHolder;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        log.info(LOG_PRE + AuthConstant.AUTHORIZATION_HEADER + request.getHeader(AuthConstant.AUTHORIZATION_HEADER));

        jwtContextHolder.set(JwtContext.of(UUID.randomUUID().toString()));

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
