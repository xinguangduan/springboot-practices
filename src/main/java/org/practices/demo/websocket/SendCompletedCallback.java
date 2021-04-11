package org.practices.demo.websocket;

import io.undertow.websockets.core.WebSocketCallback;
import io.undertow.websockets.core.WebSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendCompletedCallback<WebSocketChannel> implements WebSocketCallback {

    @Override
    public void complete(io.undertow.websockets.core.WebSocketChannel webSocketChannel, Object o) {
        log.info("message send completed,code:{}", webSocketChannel.getAttribute("code"));
    }

    @Override
    public void onError(io.undertow.websockets.core.WebSocketChannel webSocketChannel, Object o, Throwable throwable) {
        log.error("message send error,{},{}", webSocketChannel.isOpen(), webSocketChannel.getAttribute("code"), throwable);
        Common.clients.remove(webSocketChannel);
    }
}
