package com.github.halo.domainevent;

import com.github.halo.domainevent.framework.annotion.EnableDomainEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDomainEvent
public class DomainEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainEventApplication.class, args);
    }

}
