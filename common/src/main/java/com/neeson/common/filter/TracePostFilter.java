package com.neeson.common.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 21:29
 */
@Order(value = 3000)
public class TracePostFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

    }
}
