package org.practices.demo.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class Caffeine08 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Caffeine08 c = new Caffeine08();
        c.test();
        System.out.println("==========================");
        c.test1();
    }

    public void test() throws InterruptedException {
        // 初始化缓存，缓存最大个数为1
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .maximumSize(100)
                .build();

        cache.put(1, 1);
        System.out.println(cache.estimatedSize());

        // 稍微休眠一秒
        Thread.sleep(4000);
        // 打印缓存个数，结果为1
        System.out.println(cache.getIfPresent(1));
        System.out.println(cache.estimatedSize());
    }

    public void test1() throws InterruptedException {
        // 初始化缓存，设置最大权重为2
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .maximumSize(100)
                //最大容量，超过会自动清理空间
                .removalListener(((key, value, cause) -> {
                    //清理通知 key,value ==> 键值对   cause ==> 清理原因
                    System.out.println("remove translation instance from cache, reason:{}" + cause.toString());
                }))
                .build();

        cache.put(1, 1);
        // 打印缓存个数，结果为1
        System.out.println(cache.estimatedSize());
        // 稍微休眠一秒
        Thread.sleep(2000);
        System.out.println(cache.getIfPresent(1));
        Thread.sleep(2500);
        System.out.println(cache.getIfPresent(1));
        Thread.sleep(2500);
        System.out.println(cache.getIfPresent(1));
        Thread.sleep(2500);
        System.out.println(cache.getIfPresent(1));
        // 打印缓存个数，结果为1
        System.out.println(cache.estimatedSize());
        System.out.println(cache.getIfPresent(1));
        Thread.sleep(4000);
        System.out.println(cache.getIfPresent(1));
        cache.cleanUp();
    }
}
