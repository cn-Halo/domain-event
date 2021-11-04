package com.github.halo.domainevent.framework;

import lombok.extern.java.Log;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 16:43
 */
@Component
@Log
public class SpringDomainEventPublisher implements DomainEventPublisher, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void publish(List<DomainEvent> domainEvents) {
        if (applicationContext == null) {
            log.severe("没有注入applicationContext");
            return;
        }
        ApplicationEventPublisher applicationEventPublisher = (ApplicationEventPublisher) applicationContext.getBean("applicationEventPublisher");
        for (DomainEvent domainEvent : domainEvents) {
            applicationEventPublisher.publishEvent(domainEvent);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
