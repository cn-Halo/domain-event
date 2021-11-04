package com.github.halo.domainevent.framework;

/**
 * @author yzm
 * @date 2021/10/23 17:27
 * 聚合根
 * 基于单继承，多实现的原则，不写聚合根的抽象类
 * 多用组合，少用继承
 */
public interface AggregateRoot {


    Object getId();


}
