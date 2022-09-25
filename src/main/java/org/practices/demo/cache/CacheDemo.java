package org.practices.demo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;

@Slf4j
public class CacheDemo {
    static Cache<String, String> cache2 = Caffeine.newBuilder().build();

    public static void main(String[] args) {
        String key = "user01";
        String value = "111111111+222222";
        String key1 = "user02";
        String value1 = "6+22222215";


        for (int i = 0; i < 10; i++) {
            final String nKey = key + i;
            log.debug("key:{},value:{}", nKey, value);
            cache2.put(key + i, value + i);
        }

//        for (int i = 0; i < 5; i++) {
//            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(9));
//            final String nKey = key + i;
//            log.debug("key:{}", cache2.getIfPresent(nKey));
//        }
        cache2.put(key, value);
        //   LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(9));
        log.debug("key:{}", cache2.getIfPresent(key));
        cache2.put(key1, value1);
        cache2.invalidate(key1);

        // LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));
        log.debug("key:{}", cache2.getIfPresent(key1));

        for (int i = 0; i < 10; i++) {
            log.info("find from cache:{}" , cache2.getIfPresent(key + i));
        }


        log.debug(cache2.stats().toString());
        log.debug(String.valueOf(cache2.estimatedSize()));


    }
}
