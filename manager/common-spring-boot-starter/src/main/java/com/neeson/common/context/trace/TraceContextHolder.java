package com.neeson.common.context.trace;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.neeson.common.context.ContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Create on 2019-09-23
 *
 * @author DaiLe
 */
@Component
public class TraceContextHolder implements ContextHolder<TraceContext> {


    private static TransmittableThreadLocal<TraceContext> traceContextTransmittableThreadLocal = new TransmittableThreadLocal<>();


    @Override
    public void set(TraceContext traceContext) {
        Assert.notNull(traceContext, "traceContext 不能为空");
        traceContextTransmittableThreadLocal.set(traceContext);
    }


    @Override
    public void clear() {
        traceContextTransmittableThreadLocal.remove();
    }


    @Override
    public TraceContext get() {
        return traceContextTransmittableThreadLocal.get();
    }

}
