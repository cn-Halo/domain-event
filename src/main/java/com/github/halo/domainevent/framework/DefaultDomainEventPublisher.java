package com.github.halo.domainevent.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2021/11/4.
 *
 * @author yzm
 */
@Component
public class DefaultDomainEventPublisher implements DomainEventPublisher {

    @Autowired
    private SpringDomainEventPublisher springDomainEventPublisher;


    @Override
    public void publish(List<DomainEvent> domainEvents) {
        springDomainEventPublisher.publish(domainEvents);
    }
}
