package com.github.halo.domainevent.framework.service;

import com.alibaba.fastjson.JSON;
import com.github.halo.domainevent.framework.DomainEvent;
import com.github.halo.domainevent.framework.dto.OutboxDto;
import com.github.halo.domainevent.framework.entity.Outbox;
import com.github.halo.domainevent.framework.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 15:26
 */
@Service
public class OutboxService {

    @Autowired
    private OutboxRepository outboxRepository;


    @Transactional
    public List<DomainEvent> replay() throws Exception {
        List<Outbox> outboxes = outboxRepository.findByPublished("N");
        List<DomainEvent> domainEvents = new ArrayList<>();
        for (Outbox outbox : outboxes) {
            domainEvents.add(outbox.replay());
        }
        //todo 待完善 重放成功则认为事件发布成功 ，事件发布成功可以由事件发布器来保证
        List<Outbox> publishedOutboxes = new ArrayList<>();
        for (Outbox outbox : outboxes) {
            publishedOutboxes.add(outbox.eventPublished());
        }
        if (publishedOutboxes.size() > 0) {
            outboxRepository.saveAll(publishedOutboxes);
        }
        return domainEvents;
    }

    public void publish(Long aggregateId, Class aggregateType, List<DomainEvent> domainEvents) {
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
