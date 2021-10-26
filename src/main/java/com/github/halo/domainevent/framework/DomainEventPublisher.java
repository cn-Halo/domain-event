package com.github.halo.domainevent.framework;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/23 17:33
 * 领域事件发布器
 */
public interface DomainEventPublisher {

//    void publish(Class aggregateType, Object aggregateId, List<DomainEvent> domainEvents);

    void publish(List<DomainEvent> domainEvents);
}
