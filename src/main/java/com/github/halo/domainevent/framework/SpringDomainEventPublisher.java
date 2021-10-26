package com.github.halo.domainevent.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 16:43
 */
@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void publish(List<DomainEvent> domainEvents) {
        for (DomainEvent domainEvent : domainEvents) {
            applicationEventPublisher.publishEvent(domainEvent);
        }
    }
}
