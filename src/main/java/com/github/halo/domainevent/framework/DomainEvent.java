package com.github.halo.domainevent.framework;

import com.alibaba.fastjson.JSON;
import com.github.halo.domainevent.framework.util.idUtil;
import lombok.Data;

/**
 * @author yzm
 * @date 2021/10/23 17:31
 */
@Data
public class DomainEvent {
    public DomainEvent() {

    }

    private String aggregateId;
    private String aggregateType;
    private String eventType;
    private String eventData;
    private String eventId;

    public final static DomainEvent of(Class eventType, AggregateRoot aggregateRoot) {
        DomainEvent domainEvent = new DomainEvent();
        domainEvent.setAggregateId(String.valueOf(aggregateRoot.getId()));
        domainEvent.setAggregateType(aggregateRoot.getClass().getName());
        domainEvent.setEventType(eventType.getName());
        domainEvent.setEventData(eventData(aggregateRoot));
        domainEvent.setEventId(String.valueOf(idUtil.nextId()));
        return domainEvent;
    }

    protected static String eventData(AggregateRoot aggregateRoot) {
        return JSON.toJSONString(aggregateRoot);
    }

//    public abstract String getAggregateId();
//
//    public abstract String getAggregateType();
//
//    public abstract String getEventType();
//
//    public abstract String getEventData();


}
