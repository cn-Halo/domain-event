package com.github.halo.domainevent.test;

import com.github.halo.domainevent.framework.ResultWithDomainEvents;
import com.github.halo.domainevent.framework.service.OutboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yzm
 * @date 2021/10/26 14:15
 */
@SpringBootTest
@Rollback(value = false)
public class Test {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OutboxService outboxService;

    @Transactional
    @org.junit.jupiter.api.Test
    public void test() {
        ResultWithDomainEvents result = Order.createOrder("123");
        Order order = (Order) result.result();
        orderRepository.save(order);
        outboxService.publish(order.getId(), order.getClass(), result.events());
    }


}
