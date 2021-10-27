package com.github.halo.domainevent.test;

import com.github.halo.domainevent.framework.service.LoopQueryRelay;
import com.github.halo.domainevent.framework.service.OutboxService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzm
 * @date 2021/10/27 10:20
 */
@Configuration
public class Config {

    @Bean
    public LoopQueryRelay getLoopQueryRelay(OutboxService outboxService) {
        return new LoopQueryRelay(outboxService);
    }
}
