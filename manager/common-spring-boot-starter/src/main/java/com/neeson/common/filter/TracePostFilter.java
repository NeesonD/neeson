package com.neeson.common.filter;

import cn.hutool.core.util.StrUtil;
import com.neeson.common.constant.FilterOrderConstant;
import com.neeson.common.context.trace.TraceContext;
import com.neeson.common.context.trace.TraceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import static com.neeson.common.constant.HttpConstant.TRACE_ID;
import static com.neeson.common.constant.LogConstant.LOG_PRE;


/**
 * @author daile
 * @version 1.0
 * @date 2020/3/21 21:29
 */
@Slf4j
@Order(value = FilterOrderConstant.POST_CONTEXT)
public class TracePostFilter implements Filter {

    @Autowired
    private TraceContextHolder traceContextHolder;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String traceId = getTraceId(request);

        log.info(LOG_PRE + traceId);

        traceContextHolder.set(TraceContext.of(traceId));

        filterChain.doFilter(servletRequest,servletResponse);
    }

    private String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(TRACE_ID);
        if (StrUtil.isEmpty(traceId)) {
            return UUID.randomUUID().toString();
        }
        return traceId;
    }
}
