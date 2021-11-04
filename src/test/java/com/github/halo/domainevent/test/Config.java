package com.github.halo.domainevent.test;

import com.github.halo.domainevent.framework.DomainEventPublisherManager;
import com.github.halo.domainevent.framework.outbox.EventBus;
import com.github.halo.domainevent.framework.relay.LoopQueryRelay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzm
 * @date 2021/10/27 10:20
 */
@Configuration
public class Config {

    @Bean
    public LoopQueryRelay getLoopQueryRelay(EventBus eventBus, DomainEventPublisherManager domainEventPublisherManager) {
        return new LoopQueryRelay(eventBus, domainEventPublisherManager);
    }
}
