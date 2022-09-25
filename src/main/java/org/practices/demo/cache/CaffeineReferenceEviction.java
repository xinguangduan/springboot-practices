package org.practices.demo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 软引用与弱引用
 *
 * 软引用：如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。
 *
 * 弱引用：弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存
 */
public class CaffeineReferenceEviction {

    public void testWeak() throws InterruptedException {
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                // 设置Key为弱引用，生命周期是下次gc的时候
                .weakKeys()
                // 设置value为弱引用，生命周期是下次gc的时候
                .weakValues()
                .build();
        cache.put(1, 2);
        System.out.println(cache.getIfPresent(1));

        // 强行调用一次GC
        System.gc();

        Thread.sleep(4000);
        System.out.println(cache.getIfPresent(1));
    }

    public void testSoft() {
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                // 设置value为软引用，生命周期是GC时并且堆内存不够时触发清除
                .softValues()
                .build();
        cache.put(1, 2);
        System.out.println(cache.getIfPresent(1));

        // 强行调用一次GC
        System.gc();

        System.out.println(cache.getIfPresent(1));
    }

    public static void main(String[] args) throws InterruptedException {
        //System.gc() 不一定会真的触发GC，只是一种通知机制，但是并非一定会发生GC，垃圾收集器进不进行GC是不确定的，所以有概率看到设置weakKeys了却在调用System.gc() 的时候却没有丢失缓存数据的情况。
        //使用异步加载的方式不允许使用引用淘汰机制，启动程序的时候会报错：java.lang.IllegalStateException: Weak or soft values can not be combined with AsyncCache，猜测原因是异步加载数据的生命周期和引用淘汰机制的生命周期冲突导致的，因而Caffeine不支持。
        //使用引用淘汰机制的时候，判断两个key或者两个value是否相同，用的是 ==，而非是equals()，也就是说需要两个key指向同一个对象才能被认为是一致的，这样极可能导致缓存命中出现预料之外的问题。

        CaffeineReferenceEviction c = new CaffeineReferenceEviction();
        c.testSoft();
        c.testWeak();
    }
}
