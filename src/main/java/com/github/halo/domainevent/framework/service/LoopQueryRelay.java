package com.github.halo.domainevent.framework.service;

import com.github.halo.domainevent.framework.DomainEvent;
import com.github.halo.domainevent.framework.DomainEventPublisherManager;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 15:39
 */

public class LoopQueryRelay implements EventRelay {


    private OutboxService outboxService;

    private DomainEventPublisherManager domainEventPublisherManager = DomainEventPublisherManager.getInstance();
    private Thread worker = new Thread(new EventLooper(), "loop-outbox-thread");

    public LoopQueryRelay(OutboxService outboxService) {
        this.outboxService = outboxService;
        worker.start();
    }

//    @PostConstruct
//    public void init() {
//        worker.start();
//    }

    class EventLooper implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                try {
                    synchronized (OutboxService.blockObject) {
                        OutboxService.blockObject.wait();
                    }
                    List<DomainEvent> domainEvents = outboxService.replay();
                    //todo wait on outbox process
                    domainEventPublisherManager.publish(domainEvents);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
