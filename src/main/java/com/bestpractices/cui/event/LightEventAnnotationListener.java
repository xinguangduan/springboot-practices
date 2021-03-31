package com.bestpractices.cui.event;

import com.bestpractices.cui.entity.SmartHomeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LightEventAnnotationListener {
    @EventListener(condition = "#event.getEventCategory()=='" + EventCategoryConstant.CUI_CONFIG_SYNC + "'")
    public void handler(SmartHomeEvent event) {
        log.info("traffic light：" + event.getEventCategory());
        log.info(Thread.currentThread().getName());
    }
}