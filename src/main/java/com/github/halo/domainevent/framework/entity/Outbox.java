package com.github.halo.domainevent.framework.entity;


import com.alibaba.fastjson.JSON;
import com.github.halo.domainevent.framework.AggregateRoot;
import com.github.halo.domainevent.framework.DomainEvent;
import com.github.halo.domainevent.framework.dto.OutboxDto;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.Constructor;

/**
 * @author yzm
 * @date 2021/10/23 18:59
 * 事务性发件箱
 */
@Data
@Entity(name = "T_Outbox")
public class Outbox {

    @Id
    @GeneratedValue(generator = "snowFlakeIdGenerator")
    @GenericGenerator(name = "snowFlakeIdGenerator", strategy = "com.github.halo.domainevent.framework.util.SnowFlakeIdGenerator")
    private Long eventId; //事件ID

    private String eventType;//事件类型

    private String entityType;//聚合根类型
    private Long entityId;//聚合根id
    private String eventData; //json序列化
    private String published;// Y/N是否发布


    public static Outbox create(OutboxDto dto) {
        Outbox outbox = new Outbox();
        outbox.setEventType(dto.getEventType());
        outbox.setEntityType(dto.getEntityType());
        outbox.setEntityId(dto.getEntityId());
        outbox.setEventData(dto.getEventData());
        outbox.setPublished("N");
        return outbox;
    }


    public DomainEvent replay() throws Exception {
        Class<AggregateRoot> aClass = (Class<AggregateRoot>) Class.forName(this.getEntityType());
        AggregateRoot aggregateRoot = JSON.parseObject(this.getEventData(), aClass);

        Constructor c = Class.forName(this.getEventType()).getDeclaredConstructor(AggregateRoot.class);
        DomainEvent domainEvent = (DomainEvent) c.newInstance(aggregateRoot);
        return domainEvent;
    }

    public Outbox eventPublished() {
        this.setPublished("Y");
        return this;
    }

}
