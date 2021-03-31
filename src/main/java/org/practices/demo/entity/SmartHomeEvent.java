package org.practices.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public class SmartHomeEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    @Setter
    @Getter
    private String eventCategory;

    public SmartHomeEvent(Object source) {
        super(source);
    }

    public SmartHomeEvent(Object source, String eventCategory) {
        super(source);
        this.setEventCategory(eventCategory);
    }
}