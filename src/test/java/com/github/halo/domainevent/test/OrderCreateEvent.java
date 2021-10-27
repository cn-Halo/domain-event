package com.github.halo.domainevent.test;


import com.github.halo.domainevent.framework.DomainEvent;

/**
 * @author yzm
 * @date 2021/10/26 14:23
 */
public class OrderCreateEvent extends DomainEvent<Order> {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public OrderCreateEvent(Order source) {
        super(source);
    }
}
