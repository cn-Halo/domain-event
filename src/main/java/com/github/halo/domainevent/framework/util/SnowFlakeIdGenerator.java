package com.github.halo.domainevent.framework.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author yzm
 * @date 2021/10/26 08:58
 */
public class SnowFlakeIdGenerator implements IdentifierGenerator {
    private SnowFlake snowFlake;

    public SnowFlakeIdGenerator() {
        snowFlake = new SnowFlake(1, 1);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return snowFlake.nextId();
    }
}
