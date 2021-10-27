package com.github.halo.domainevent.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yzm
 * @date 2021/10/26 14:19
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
