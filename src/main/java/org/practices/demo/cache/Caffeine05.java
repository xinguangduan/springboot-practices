package org.practices.demo.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class Caffeine05 {
    public static void main(String[] args) {
        Cache<String, MyObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build();

        // 查找一个缓存元素， 没有查找到的时候返回null
        MyObject graph = cache.getIfPresent("key1");
        // 查找缓存，如果缓存不存在则生成缓存元素,  如果无法生成则返回null
        graph = cache.get("key1", Caffeine05::apply);
        // 添加或者更新一个缓存元素
        cache.put("key", graph);
        // 移除一个缓存元素
        cache.invalidate("key");
    }

    private static MyObject apply(String k) {
        return new MyObject();
    }

    public static class MyObject {
    }
}
