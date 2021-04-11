package com.bestpractices;

import io.undertow.websockets.core.WebSocketFrameType;
import io.undertow.websockets.core.WebSockets;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.practices.demo.websocket.Common;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketTest {
    public static void main(String[] args) {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        String flag = UUID.randomUUID().toString();
                        if (Common.clients.size() < Common.totalSize) {
                            log.info("current channels: {} ", Common.clients.size());

                        } else {
                            log.info("send msg to channels for ");
                            Common.clients.forEach(channel -> {
                                WebSockets.sendText(System.currentTimeMillis() + "", channel, null);
                            });
                            log.info("sent msg to channels for $flag. current websockets: {}", Common.clients.size());
                        }
                    }
                },
                Common.delay, Common.interval, TimeUnit.MILLISECONDS);
    }

}
