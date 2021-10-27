package com.github.halo.domainevent.framework.service;

import javax.persistence.*;

/**
 * @author yzm
 * @date 2021/10/26 22:29
 */
public class OutboxListener {


    /**
     * 持久操作（存储）之前，同步调用。
     *
     * @param o
     */
    @PrePersist
    public void prePersist(Object o) {
        System.out.println("pre persist");
    }

    /**
     * 删除操作之前，同步调用
     *
     * @param o
     */
    @PreRemove
    public void preRemove(Object o) {
        System.out.println("pre remove");
    }

    /**
     * 该方法在数据库执行完INSERT操作之后执行
     *
     * @param o
     */
    @PostPersist
    public void postPersist(Object o) {
        System.out.println("post persist");
    }

    /**
     * 删除操作执行之后，同步调用。
     *
     * @param o
     */
    @PostRemove
    public void postRemove(Object o) {
        System.out.println("post remove");
    }

    /**
     * 在数据库UPDATE操作之前执行。
     *
     * @param o
     */
    @PreUpdate
    public void preUpdate(Object o) {
        System.out.println("pre update");
    }

    /**
     * 在数据库UPDATE操作之后执行。
     *
     * @param o
     */
    @PostUpdate
    public void postUpdate(Object o) {
        System.out.println("post Update");
    }

    /**
     * 在实体被加载到当前的持久化上下文或者实体已经被刷新之后。
     *
     * @param o
     */
    @PostLoad
    public void postLoad(Object o) {
        System.out.println("post load");
    }
}
