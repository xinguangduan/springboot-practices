package org.practices.demo.entity;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;

public class SneakyThrowsDemo implements Runnable {
    @SneakyThrows(UnsupportedEncodingException.class)
    public String utf8ToString(byte[] bytes) {
        return new String(bytes, "UTF-8");
    }

    @SneakyThrows
    public void run() {
        throw new Throwable();
    }

    public static void main(String[] args) {
        SneakyThrowsDemo sneaky = new SneakyThrowsDemo();
        sneaky.utf8ToString(null);
        //sneaky.run();
    }
}

