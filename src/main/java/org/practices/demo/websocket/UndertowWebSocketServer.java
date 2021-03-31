package org.practices.demo.websocket;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.*;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static io.undertow.Handlers.*;

@Slf4j
public class UndertowWebSocketServer {
    public static void main(String[] args) {

        Undertow server = Undertow.builder()
                .addHttpListener(Common.port, Common.host)
                .setHandler(path()
                        .addPrefixPath("/ws", websocket(new WebSocketConnectionCallback() {

                            @Override
                            public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
                                Common.clients.add(channel);
                                channel.getReceiveSetter().set(new AbstractReceiveListener() {

                                    @Override
                                    protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                        String messageData = message.getData();
                                        WebSockets.sendText(messageData, channel, null);
                                        log.info("message event data -------------------------:{}", messageData);
                                    }

                                    @Override
                                    protected void onPing(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                                        super.onPing(webSocketChannel, channel);
                                    }

                                    @Override
                                    protected void onClose(WebSocketChannel webSocketChannel, StreamSourceFrameChannel channel) throws IOException {
                                        super.onClose(webSocketChannel, channel);
                                    }

                                    @Override
                                    protected void onPong(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                        super.onPong(webSocketChannel, messageChannel);
                                    }

                                    @Override
                                    protected void onText(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                        super.onText(webSocketChannel, messageChannel);
                                    }

                                    @Override
                                    protected void onBinary(WebSocketChannel webSocketChannel, StreamSourceFrameChannel messageChannel) throws IOException {
                                        super.onBinary(webSocketChannel, messageChannel);
                                    }

                                    @Override
                                    protected void onError(WebSocketChannel channel, Throwable error) {
                                        super.onError(channel, error);
                                    }

                                    @Override
                                    protected void onFullBinaryMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                        super.onFullBinaryMessage(channel, message);
                                    }

                                    @Override
                                    protected void onFullPingMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                        super.onFullPingMessage(channel, message);
                                    }

                                    @Override
                                    protected void onFullPongMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                        super.onFullPongMessage(channel, message);
                                    }

                                    @Override
                                    protected void onFullCloseMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                                        super.onFullCloseMessage(channel, message);
                                    }

                                    @Override
                                    protected void onCloseMessage(CloseMessage cm, WebSocketChannel channel) {
                                        super.onCloseMessage(cm, channel);
                                        Common.clients.remove(channel);
                                    }
                                });
                                channel.resumeReceives();
                            }
                        }))
                        .addPrefixPath("/", resource(new ClassPathResourceManager(UndertowWebSocketServer.class.getClassLoader(), UndertowWebSocketServer.class.getPackage())).addWelcomeFiles("index.html")))
                .build();
        server.start();
        log.info("started");
    }

}



