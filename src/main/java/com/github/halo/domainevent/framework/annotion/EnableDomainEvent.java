package com.github.halo.domainevent.framework.annotion;

import com.github.halo.domainevent.framework.config.DefaultOutboxConfigure;
import org.springframework.context.annotation.Import;

/**
 * Created on 2021/11/4.
 *
 * @author yzm
 */
@Import({DefaultOutboxConfigure.class})
public @interface EnableDomainEvent {

}
