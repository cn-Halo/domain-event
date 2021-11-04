package com.github.halo.domainevent.test;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author yzm
 * @date 2021/10/26 14:55
 */
public class OrderListener implements ApplicationListener {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("111");

    }
}
