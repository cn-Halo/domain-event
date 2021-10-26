package com.github.halo.domainevent.framework.service;

import com.github.halo.domainevent.framework.DomainEvent;
import com.github.halo.domainevent.framework.DomainEventPublisherManager;
import com.github.halo.domainevent.framework.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 15:39
 */
@Service
public class LoopQueryReplayer implements EventReplayer {

    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private OutboxService outboxService;
    @Autowired
    private DomainEventPublisherManager domainEventPublisherManager;

    private Thread worker = new Thread("loop-outbox-thread");

    public LoopQueryReplayer() {
        worker.start();
    }

    @Override
    public List<DomainEvent> replay() {
        try {
            outboxService.replay();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    class EventLooper implements Runnable {

        @Override
        public void run() {
            try {
                //todo 获取事务
                List<DomainEvent> domainEvents = outboxService.replay();
                //todo wait on outbox process
                domainEventPublisherManager.publish(domainEvents);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
