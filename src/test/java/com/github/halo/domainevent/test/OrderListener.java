package com.github.halo.domainevent.test;

import org.springframework.context.ApplicationListener;

/**
 * @author yzm
 * @date 2021/10/26 14:55
 */
public class OrderListener implements ApplicationListener<OrderCreateEvent> {

    @Override
    public void onApplicationEvent(OrderCreateEvent event) {
        System.out.println("111");
    }
}
