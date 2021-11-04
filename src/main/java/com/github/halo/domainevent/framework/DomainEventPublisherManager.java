package com.github.halo.domainevent.framework;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 16:37
 */
@Component
public class DomainEventPublisherManager {

    private List<DomainEventPublisher> publishers = new ArrayList<>();

    public DomainEventPublisherManager(DefaultDomainEventPublisher defaultDomainEventPublisher) {
        if (defaultDomainEventPublisher != null) {
            this.register(defaultDomainEventPublisher);
        }
    }

    public void register(DomainEventPublisher publisher) {
        publishers.add(publisher);
    }

    private volatile static DomainEventPublisherManager instance;

    public static DomainEventPublisherManager getInstance(DefaultDomainEventPublisher defaultDomainEventPublisher) {
        if (instance == null) {
            synchronized (DomainEventPublisherManager.class) {
                if (instance == null) {
                    instance = new DomainEventPublisherManager(defaultDomainEventPublisher);
                }
            }
        }
        return instance;
    }

    public static DomainEventPublisherManager getInstance() {
        return getInstance(null);
    }

    public void publish(List<DomainEvent> domainEvents) {
        for (DomainEventPublisher publisher : publishers) {
            publisher.publish(domainEvents);
        }
    }

}
