package org.practices.demo.jredisjson;

import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * RedisJson测试类
 * </p>
 *
 * @author: liuchangjun
 * @since: 2021/12/22 15:50
 */
@Slf4j
public class RedisJsonTest {

    public static void test() {
        // 获取连接
        JReJSON client = new JReJSON("127.0.0.1", 6379);

        // 添加字符串(路径为根路径)并返回
        client.set("name", "lcj", Path.ROOT_PATH);
        String name = client.get("name");
        log.info("字符串name:{}", name);

        name = client.get("name", String.class, Path.of("."));
        log.info("字符串name:{}", name);

        name = client.get("name", String.class, Path.ROOT_PATH);
        log.info("字符串name:{}", name);


//        for (int i = 0; i < 10; i++) {
//            client.set("token" + i, "xxxxxxxxxxxxx" + i);
//        }

        for (int i = 0; i < 10; i++) {
            log.info(client.get("token" + i));
        }
    }

    public static void main(String[] args) {
        test();
    }
}
