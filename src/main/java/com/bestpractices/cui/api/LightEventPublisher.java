package com.bestpractices.cui.api;

import com.bestpractices.cui.entity.SmartHomeEvent;
import com.bestpractices.cui.entity.SyncCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LightEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(LightEventPublisher.class);

    /**
     * 这里注入 ApplicationContext 和 ApplicationEventPublisher 是等价的，后者是一个接口，前者继承了该接口
     * 也就是说 ApplicationContext 本身提供了发布 event 的能力
     */

    //@Autowired
    //private ApplicationContext context;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @RequestMapping("/publish")
    public Object publish(){
        SmartHomeEvent lightEvent = new SmartHomeEvent("", "CUI_CONFIG_SYNC");
        applicationEventPublisher.publishEvent(lightEvent);
        log.info("things after publish");
        return "ok";
    }
}