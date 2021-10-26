package com.github.halo.domainevent.framework.dto;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author yzm
 * @date 2021/10/23 20:38
 */
@Data
public class OutboxDto {

    private String eventType;
    private String entityType;
    private Long entityId;
    private String eventData; //json序列化

}
