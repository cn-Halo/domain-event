package com.github.halo.domainevent.framework;

import com.alibaba.fastjson.JSON;
import com.github.halo.domainevent.framework.dto.OutboxDto;
import com.github.halo.domainevent.framework.entity.Outbox;
import com.github.halo.domainevent.framework.repository.OutboxRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzm
 * @date 2021/10/23 17:34
 */
public abstract class AbstractAggregateDomainEventPublisher<A extends AggregateRoot, E extends DomainEvent> {

    private DomainEventPublisher domainEventPublisher;
    private OutboxRepository outboxRepository;

    public void publish(Long aggregateId, Class<A> aggregateType, List<E> domainEvents) {
        List<Outbox> outboxes = new ArrayList<>();
        for (DomainEvent domainEvent : domainEvents) {
            OutboxDto outboxDto = new OutboxDto();
            //todo eventData的序列化
            outboxDto.setEventData(JSON.toJSONString(domainEvent.getSource()));
            //全限定名 带包名
            outboxDto.setEventType(domainEvent.getClass().getName());
            outboxDto.setEntityId(aggregateId);
            outboxDto.setEntityType(aggregateType.getName());
            outboxes.add(Outbox.create(outboxDto));
        }
        outboxRepository.saveAll(outboxes);
    }


}
