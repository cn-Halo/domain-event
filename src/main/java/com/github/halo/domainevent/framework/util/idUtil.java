package com.github.halo.domainevent.framework.util;

/**
 * Created on 2021/11/4.
 *
 * @author yzm
 */
public class idUtil {

    static SnowFlake snowFlake;

    static {
        snowFlake = new SnowFlake(1, 1);
    }

    public static long nextId() {
        return snowFlake.nextId();
    }

}
