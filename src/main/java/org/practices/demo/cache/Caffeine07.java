package org.practices.demo.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class Caffeine07 {
    public static void main(String[] args) {
        // 基于固定的过期时间驱逐策略
        LoadingCache<String, String> graphs = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));
        LoadingCache<String, String> graphs1 = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(key -> createExpensiveGraph(key));

        // 基于不同的过期驱逐策略
        LoadingCache<String, String> graphs2 = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, String>() {
                    public long expireAfterCreate(String key, String graph, long currentTime) {
                        // Use wall clock time, rather than nanotime, if from an external resource
                        long seconds = 10;
                        return TimeUnit.SECONDS.toNanos(seconds);
                    }

                    public long expireAfterUpdate(String key, String graph,
                                                  long currentTime, long currentDuration) {
                        return currentDuration;
                    }

                    public long expireAfterRead(String key, String graph,
                                                long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                })
                .build(key -> createExpensiveGraph(key));
    }

    private static String createExpensiveGraph(String key) {
        return key;
    }
}
