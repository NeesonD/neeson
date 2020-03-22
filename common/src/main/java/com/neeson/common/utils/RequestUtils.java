package com.neeson.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author daile
 * @version 1.0
 * @date 2020/3/22 14:59
 */
public class RequestUtils {

    /**
     * 获取 HttpServletRequest
     * @return
     */
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes())
                .getRequest();
    }

    /**
     * 获取当前请求session
     * @return
     */
    public static HttpSession getHttpSession(){
        return getHttpServletRequest().getSession();
    }


}
