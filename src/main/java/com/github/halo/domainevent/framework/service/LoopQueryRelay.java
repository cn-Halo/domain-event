package com.github.halo.domainevent.framework.service;

import com.github.halo.domainevent.framework.DomainEvent;
import com.github.halo.domainevent.framework.DomainEventPublisherManager;
import com.github.halo.domainevent.framework.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 15:39
 */
@Service
public class LoopQueryRelay implements EventRelay {

    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private OutboxService outboxService;
    @Autowired
    private DomainEventPublisherManager domainEventPublisherManager;

    private Thread worker = new Thread(new EventLooper(), "loop-outbox-thread");

//    public LoopQueryRelay() {
//        worker.start();
//    }

    @PostConstruct
    public void init() {
        worker.start();
    }

//    @Override
//    public List<DomainEvent> replay() {
//        try {
//            outboxService.replay();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    class EventLooper implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                try {
                    List<DomainEvent> domainEvents = outboxService.replay();
                    //todo wait on outbox process
                    domainEventPublisherManager.publish(domainEvents);
//                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
