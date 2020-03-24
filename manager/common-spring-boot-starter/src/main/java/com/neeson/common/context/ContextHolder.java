package com.neeson.common.context;

/**
 * Create on 2020-03-20
 *
 * @author Administrator
 */
public interface ContextHolder<T> {

    /**
     * 设置上下文
     * @param t
     */
    void set(T t);

    /**
     * 获取上下文
     * @return
     */
    T get();

    /**
     * 清理上下文
     */
    void clear();

}
