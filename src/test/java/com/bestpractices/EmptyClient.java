package com.bestpractices;


import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EmptyClient extends WebSocketClient {

    public EmptyClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public EmptyClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario");
        log.info("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        log.info("received message: " + message);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        log.info("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        log.error("an error occurred:" + ex);
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            WebSocketClient client = new EmptyClient(new URI("ws://localhost:8887/ws"));
            client.connect();
        }

    }
}
