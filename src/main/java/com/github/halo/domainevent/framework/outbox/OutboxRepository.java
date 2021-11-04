package com.github.halo.domainevent.framework.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/23 19:36
 */
@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findByPublished(String published);

    @Modifying
    @Query("update Outbox m set m.published = ?1 where  m.eventId in ?2")
    void updatePublishedByEventIdIn(String published, List<String> eventIds);
}
