package org.practices.demo.entity;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@ToString
@Slf4j
public class StaticDemo {
    private static final int STATIC_VAR = 10;
    private String name;
    private Shape shape = new Square(5, 10);
    private String[] tags;
    @ToString.Exclude
    private int id;

    static String spec = "hide me";
    String comm = "show me";

    public String getName() {
        return this.name;
    }

    @ToString(callSuper = true, includeFieldNames = true)
    public static class Square extends Shape {
        private final int width, height;

        public Square(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static void main(String[] args) {
        StaticDemo toStringExample = new StaticDemo();
        log.info("toStringExample.toString() :" + toStringExample.toString());
    }

}

class Shape {

}
