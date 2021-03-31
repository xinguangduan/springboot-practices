package org.practices.demo.websocket;

import io.undertow.websockets.core.WebSocketChannel;

import java.util.HashSet;
import java.util.Set;

public class Common {
    public static boolean onlyTestConnect = true;
    public static Set<WebSocketChannel> clients = new HashSet<WebSocketChannel>();
    public static int totalSize = 10;

    public static String host = "127.0.0.1";
    public static int port = 8887;
    public static int delay = 1000;
    public static int interval = 1000;
}
