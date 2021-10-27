package com.github.halo.domainevent.test;

import com.github.halo.domainevent.framework.AggregateRoot;
import com.github.halo.domainevent.framework.ResultWithDomainEvents;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;

/**
 * @author yzm
 * @date 2021/10/26 14:16
 */
@Data
@Entity
@Table(name = "T_Order")
public class Order implements AggregateRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;


    public static ResultWithDomainEvents<Order,OrderCreateEvent> createOrder(String orderNo) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        return new ResultWithDomainEvents<>(order, Collections.singletonList(new OrderCreateEvent(order)));
    }
}
