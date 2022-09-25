package org.practices.demo.entity;

import lombok.Getter;
import lombok.extern.java.Log;

/**
 * @Getter(lazy = true)
 * 标注字段为懒加载字段，懒加载字段在创建对象时不会进行真正的初始化，而是在第一次访问的时候才会初始化，后面再次访问也不会重复初始化
 * 如果Bean的一个字段的初始化是代价比较高的操作，比如加载大量的数据；同时这个字段并不是必定使用的。那么使用懒加载机制，可以保证节省资源。
 */
@Log
public class GetterLazyExample {
    @Getter(lazy = true)
    private final double[] cached = expensive();

    private double[] expensive() {
        double[] result = new double[1000000];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.asin(i);
        }
        return result;
    }

    public static void main(String[] args) {
        GetterLazyExample lazyExample = new GetterLazyExample();
        log.info(lazyExample.expensive().length + "");
    }
}