package org.practices.demo.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * 需要用到写后一段时间定时过期，可是如果在一定时间内，数据有访问则重新计时，应该怎么做呢,Caffeine提供的刷新机制了，使用很简单，用接口refreshAfterWrite 即可
 */
public class CaffeineRefresh {

    private int index = 0;

    /**
     * 模拟从数据库中读取数据
     *
     * @return
     */
    private int getInDB() {
        // 这里为了体现数据重新被get，因而用了index++
        index++;
        System.out.println("load DB " + index);
        return index;
    }

    public void test() throws InterruptedException {
        // 设置写入后3秒后数据过期，2秒后如果有数据访问则刷新数据
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull Integer key) {
                        return getInDB();
                    }
                });
        cache.put(1, getInDB());

        // 休眠2.5秒，后取值
        Thread.sleep(2500);
        System.out.println(cache.getIfPresent(1));

        // 休眠1.5秒，后取值
        Thread.sleep(1500);
        System.out.println(cache.getIfPresent(1));
    }

    public static void main(String[] args) throws InterruptedException {
        CaffeineRefresh refresh = new CaffeineRefresh();
        refresh.test();
    }
}

