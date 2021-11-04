package com.github.halo.domainevent.framework.outbox;

import com.github.halo.domainevent.framework.DomainEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yzm
 * @date 2021/11/3 22:14
 * 事件总线
 */
@Component
public class EventBus {

    private OutboxRepository outboxRepository;

    public static Object blockObject = new Object();

    public EventBus(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    public void fire(List<DomainEvent> domainEvents) {
        persist(domainEvents);
        synchronized (blockObject) {
            blockObject.notifyAll();
        }
    }

    public void fire(DomainEvent domainEvent) {
        fire(Collections.singletonList(domainEvent));
    }

    /**
     * 支持当前事务，如果当前没有事务，就抛出异常。
     */
    @Transactional(propagation = Propagation.MANDATORY)
    private void persist(List<DomainEvent> domainEvents) {
        List<Outbox> outboxes = new ArrayList<>();
        for (DomainEvent domainEvent : domainEvents) {
            Outbox outbox = Outbox.create(domainEvent);
            outboxes.add(outbox);
        }
        outboxRepository.saveAll(outboxes);
    }

    @Transactional(readOnly = true)
    public List<DomainEvent> lookup() throws Exception {
        List<Outbox> outboxes = outboxRepository.findByPublished("N");
        List<DomainEvent> domainEvents = new ArrayList<>();
        for (Outbox outbox : outboxes) {
            domainEvents.add(outbox.replay());
//            outbox.eventPublished();
        }
        //todo 待完善 重放成功则认为事件发布成功 ，事件发布成功可以由事件发布器来保证
//        if (outboxes.size() > 0) {
//            outboxRepository.saveAll(outboxes);
//        }
        return domainEvents;
    }

    @Transactional
    public void eventPublished(List<DomainEvent> domainEvents) {
        outboxRepository.updatePublishedByEventIdIn("Y", domainEvents.stream().map(o -> o.getEventId()).collect(Collectors.toList()));
    }


}
