package org.hummer.config.event;

import org.springframework.context.ApplicationEvent;

/**
 * 配置变更事件
 *
 * @author zhujinming6
 * @create 2019-10-10 10:48
 * @update 2019-10-10 10:48
 **/
public class ConfigChangeEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ConfigChangeEvent(Object source) {
        super(source);
    }
}
