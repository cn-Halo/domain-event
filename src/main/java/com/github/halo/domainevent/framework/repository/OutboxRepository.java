package com.github.halo.domainevent.framework.repository;

import com.github.halo.domainevent.framework.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/23 19:36
 */
@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findByPublished(String published);
}
