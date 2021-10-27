package com.github.halo.domainevent.framework;

import org.springframework.context.ApplicationEvent;

/**
 * @author yzm
 * @date 2021/10/23 17:31
 */
public class DomainEvent<A extends AggregateRoot> extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DomainEvent(A source) {
        super(source);
    }

    @Override
    public A getSource() {
        return (A) super.getSource();
    }
}
