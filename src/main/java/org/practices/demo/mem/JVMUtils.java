package org.practices.demo.mem;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

@Slf4j
public class JVMUtils {

    public static void main(String[] args) {
        // gc操作
        System.gc();
// 在这里new需要测试内存占用的对象
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        Map<String, Map<String, String>> tokenMap = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            String token = i+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjdWkubGVub3ZvIiwiY3VpYm9keSI6IntcInVpZFwiOlwiXCIsXCJkZXZpY2VfaW5mb1wiOntcImNsaWVudENoYW5uZWxcIjpcInByZWxvYWRcIixcImNsaWVudFN3VmVyXCI6XCIyLjUuNDIuMFwiLFwiY3NUeXBlXCI6XCJcIixcImRldmljZWlkXCI6XCIzMjY4OEIxNi00NjdCLTExRUMtODEwRC03QzhBRTFCM0RGM0NcIixcImh3TW9kZWxcIjpcIjgyTDdcIixcIm1hY1wiOlwiMDI6NTA6RjM6RjM6Mzk6NzZcIixcInByb2R1Y3RpZFwiOlwiXCIsXCJzblwiOlwiTVAyNEs0V1dcIixcInZlbmRvclwiOlwicGNcIn0sXCJkZXZpY2VfaWRcIjpcIjMyNjg4QjE2LTQ2N0ItMTFFQy04MTBELTdDOEFFMUIzREYzQ1wiLFwiYW5vbnltb3VzX2lkXCI6XCJkNGEwZTM0NWViYzM0OTQyYjdlYTMxYTdhOTA1ZDM0N2FmNjllODhmNTE3YTc0ODdmOWY4YzYyNGVmMTk0Yzg4MGU5ZmU1MTU0OTNlNTU4YzNmYzk3ZmM4NTc3YTZmOTJcIixcInByb2R1Y3RfaWRcIjpcIlBDX0xWQVwiLFwiZXhwaXJlX2F0XCI6MCxcImNhdGVnb3J5XCI6XCJwY1wiLFwiYXBwbGljYXRpb25faWRcIjpcIjVlZmMwNGU0OGZmODY2NDJjOGY1NDc4Y1wiLFwiY3NfdHlwZVwiOlwiXCJ9Iiwibm9uY2UiOiJDbjJ5eU5qeSJ9.YyFjPAQy6JK6pj6DRz13AfcpZLzHI3ORM6ssRgwKNcI";
            Map<String, String> tokenInfo = new HashMap<>();

            long expireAt = 7200;
            tokenInfo.put("LOGINTYPE", "anonymous");
            tokenInfo.put("USERID", "anonymous_id");
            tokenInfo.put("ANONYMOUSID", "anonymous_id");
            tokenInfo.put("DEVICEID", "device_id");
            tokenInfo.put("APPLICATIONID", "K_APPLICATION_ID");
            tokenInfo.put("PRODUCTID", "product_id");
            tokenInfo.put("CATEGORY", "CATEGORY");
            tokenInfo.put("expire_at", String.valueOf(expireAt));
            tokenInfo.put("K_H2_CONNECTION_ID", "h2ConnectionId");
            tokenMap.put(token, tokenInfo);
        }

        System.out.println(GraphLayout.parseInstance(tokenMap).totalSize());

        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println(endMemory-startMemory);
    }
}
