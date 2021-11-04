package com.github.halo.domainevent.framework.outbox;


import com.github.halo.domainevent.framework.DomainEvent;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yzm
 * @date 2021/10/23 18:59
 * 事务性发件箱
 */
@Data
@Entity
@Table(name = "T_Outbox")
@EntityListeners(OutboxListener.class)
public class Outbox {

    @Id
//    @GeneratedValue(generator = "snowFlakeIdGenerator")
//    @GenericGenerator(name = "snowFlakeIdGenerator", strategy = "com.github.halo.domainevent.framework.util.SnowFlakeIdGenerator")
    private String eventId;
    private String aggregateType;//聚合根类型
    private String aggregateId;//聚合根id
    private String eventType;//事件类型
    private String eventData; //json序列化
    private String published;// Y/N是否发布


    public static Outbox create(DomainEvent event) {
        Outbox outbox = new Outbox();
        outbox.setEventType(event.getEventType());
        outbox.setAggregateType(event.getAggregateType());
        outbox.setAggregateId(event.getAggregateId());
        outbox.setEventData(event.getEventData());
        outbox.setEventId(event.getEventId());
        outbox.setPublished("N");
        return outbox;
    }

    /**
     * 重放事件
     *
     * @return
     * @throws Exception
     */
    public DomainEvent replay() throws Exception {
//        Class<AggregateRoot> aClass = (Class<AggregateRoot>) Class.forName(this.getAggregateType());
//        AggregateRoot aggregateRoot = JSON.parseObject(this.getEventData(), aClass);

//        Constructor c = Class.forName(this.getEventType()).getDeclaredConstructor(aClass);
//        DomainEvent domainEvent = (DomainEvent) c.newInstance(aggregateRoot);

        DomainEvent domainEvent = (DomainEvent) Class.forName(this.getEventType()).newInstance();
        domainEvent.setAggregateId(this.aggregateId);
        domainEvent.setAggregateType(this.aggregateType);
        domainEvent.setEventType(this.eventType);
        domainEvent.setEventData(this.eventData);
        domainEvent.setEventId(this.eventId);
        return domainEvent;
    }

    public Outbox eventPublished() {
        this.setPublished("Y");
        return this;
    }

}
