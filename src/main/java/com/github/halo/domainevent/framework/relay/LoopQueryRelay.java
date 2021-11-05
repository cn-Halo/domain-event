package com.github.halo.domainevent.framework.relay;

import com.github.halo.domainevent.framework.DomainEvent;
import com.github.halo.domainevent.framework.DomainEventPublisherManager;
import com.github.halo.domainevent.framework.outbox.EventBus;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 15:39
 */

public class LoopQueryRelay implements EventRelay {


    private EventBus eventBus;
    private DomainEventPublisherManager domainEventPublisherManager;
    //    private DomainEventPublisherManager domainEventPublisherManager = DomainEventPublisherManager.getInstance();
    private Thread worker = new Thread(new EventLooper(), "loop-outbox-thread");

    public LoopQueryRelay(EventBus eventBus, DomainEventPublisherManager domainEventPublisherManager) {
//        this.outboxService = outboxService;
        this.eventBus = eventBus;
        this.domainEventPublisherManager = domainEventPublisherManager;
        worker.start();
    }


    class EventLooper implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                try {
                    synchronized (eventBus.blockObject) {
                        eventBus.blockObject.wait();
                    }
                    List<DomainEvent> domainEvents = eventBus.lookup();
                    domainEventPublisherManager.publish(domainEvents);
                    eventBus.eventPublished(domainEvents);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
