package org.practices.demo.websocket;

import io.undertow.Undertow;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.*;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.websocket;

@Slf4j
public class UndertowWebSocketServer {
    public static void main(String[] args) {
        // build auto send  executor
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                () -> {
                    String flag = UUID.randomUUID().toString();
                    if (Common.clients.size() > 0) {
                        Common.clients.forEach(channel -> {
                            if (!channel.isOpen()) {
                                Common.clients.remove(channel);
                                return;
                            }
                            SendCompletedCallback callback = new SendCompletedCallback();
                            WebSockets.sendText(System.currentTimeMillis() + "===>" + flag, channel, callback);
                            log.info("auto send msg to channels for ===>{}. current websockets: {}", flag, Common.clients.size());
                        });
                    }else{
                        log.info("client is empty.");
                    }
                },
                Common.delay, Common.interval, TimeUnit.MILLISECONDS);
        // build server
        Undertow server = Undertow.builder()
                .addHttpListener(Common.port, Common.host)
                .setHandler(path()
                                .addPrefixPath("/ws", websocket(new WebSocketConnectionCallback() {
                                    @Override
                                    public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
                                        channel.setAttribute("code", UUID.randomUUID().toString());
                                        Common.clients.add(channel);
                                        //channel.setIdleTimeout(1000 * 5);
                                        channel.getReceiveSetter().set(new AbstractReceiveListener() {

                                            @Override
                                            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                                String messageData = message.getData();
                                                // WebSockets.sendText(messageData, channel, new SendCompletedCallback());
//                                        for (WebSocketChannel session : channel.getPeerConnections()) {
//                                            WebSockets.sendText(messageData, session, new SendCompletedCallback());
//                                        }
                                                log.info("message event data -------------------------:{}", messageData);
                                            }

                                            @Override
                                            protected void onText(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                                super.onText(webSocketChannel, messageChannel);
                                                log.info("onText,{}", webSocketChannel.getIoThread().getId());
                                            }

                                            @Override
                                            protected void onPing(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                                                super.onPing(webSocketChannel, channel);
                                                log.info("onPing,{}", channel.getWebSocketChannel().getIoThread().getId());
                                            }

                                            @Override
                                            protected void onClose(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                                                super.onClose(webSocketChannel, channel);
                                                log.info("onClose,{}", channel.getWebSocketChannel().getIoThread().getId());
                                            }

                                            @Override
                                            protected void onPong(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                                super.onPong(webSocketChannel, messageChannel);
                                                log.info("onPong,{}", webSocketChannel.getIoThread().getId());
                                            }


                                            @Override
                                            protected void onBinary(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                                super.onBinary(webSocketChannel, messageChannel);
                                                //log.info("onBinary,{}", webSocketChannel.getIoThread().getId());
                                            }

                                            @Override
                                            protected void onError(WebSocketChannel channel, Throwable error) {
                                                super.onError(channel, error);
                                                Common.clients.remove(channel);
                                                log.info("onError,{}", channel.getIoThread().getId(), error);
                                            }

                                            @Override
                                            protected void onFullBinaryMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                                log.info("onFullBinaryMessage,{},size:{}", channel.getIoThread().getId(), message.getData().getResource().length);
                                                super.onFullBinaryMessage(channel, message);

                                            }

                                            @Override
                                            protected void onFullPingMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                                super.onFullPingMessage(channel, message);
                                                log.info("onFullPingMessage,{},{}", channel.getIoThread().getId(), message);
                                            }

                                            @Override
                                            protected void onFullPongMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                                super.onFullPongMessage(channel, message);
                                                log.info("onFullPongMessage,{},{}", channel.getIoThread().getId(), message);
                                            }

                                            @Override
                                            protected void onFullCloseMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                                super.onFullCloseMessage(channel, message);
                                                log.info("onFullCloseMessage,{},{}", channel.getIoThread().getId(), message);
                                                Common.clients.remove(channel);
                                            }

                                            @Override
                                            protected void onCloseMessage(CloseMessage cm, WebSocketChannel channel) {
                                                super.onCloseMessage(cm, channel);
                                                log.info("onCloseMessage,{},{}", channel.getIoThread().getId(), cm);
                                                Common.clients.remove(channel);
                                            }
                                        });
                                        channel.resumeReceives();
                                    }
                                }))
                )
                .build();
        server.start();
        log.info("started");
    }

}



