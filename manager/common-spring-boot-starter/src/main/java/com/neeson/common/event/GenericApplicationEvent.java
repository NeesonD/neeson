package com.neeson.common.event;

import org.springframework.context.ApplicationEvent;

public class GenericApplicationEvent<T> extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public GenericApplicationEvent(T source) {
        super(source);
    }

    @Override
    public T getSource(){
        return (T) super.getSource();
    }
}
