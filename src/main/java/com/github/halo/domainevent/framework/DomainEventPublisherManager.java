package com.github.halo.domainevent.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 16:37
 */
public class DomainEventPublisherManager {

    private List<DomainEventPublisher> publishers = new ArrayList<>();

    public void register(DomainEventPublisher publisher) {
        publishers.add(publisher);
    }

    private volatile static DomainEventPublisherManager instance;

    public static DomainEventPublisherManager getInstance() {
        if (instance == null) {
            synchronized (DomainEventPublisherManager.class) {
                if (instance == null) {
                    instance = new DomainEventPublisherManager();
                }
            }
        }
        return instance;
    }

    public void publish(List<DomainEvent> domainEvents) {
        for (DomainEventPublisher publisher : publishers) {
            publisher.publish(domainEvents);
        }
    }

}
