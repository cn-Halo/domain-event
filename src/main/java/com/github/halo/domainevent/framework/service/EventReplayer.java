package com.github.halo.domainevent.framework.service;

import com.github.halo.domainevent.framework.DomainEvent;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/25 15:36
 */
public interface EventReplayer {

    /**
     * 重放事件
     *
     * @return
     */
    List<DomainEvent> replay();
}
