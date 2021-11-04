package com.github.halo.domainevent.framework.config;

import com.github.halo.domainevent.framework.DomainEventPublisherManager;
import com.github.halo.domainevent.framework.outbox.EventBus;
import com.github.halo.domainevent.framework.relay.EventRelay;
import com.github.halo.domainevent.framework.relay.LoopQueryRelay;
import org.springframework.context.annotation.Bean;

/**
 * Created on 2021/11/4.
 *
 * @author yzm
 */
public class DefaultOutboxConfigure {


    @Bean
    public EventRelay loopQueryRelay(EventBus eventBus, DomainEventPublisherManager domainEventPublisherManager) {
        EventRelay eventRelay = new LoopQueryRelay(eventBus, domainEventPublisherManager);
        return eventRelay;
    }


}
