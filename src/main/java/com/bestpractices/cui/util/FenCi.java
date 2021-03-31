package com.bestpractices.cui.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FenCi {
    private static final Map<String, String> trans = new ConcurrentHashMap<>();

    static {
        trans.put("投射", "投屏");
        trans.put("開始", "开启");
        trans.put("設定", "设置");
    }

    public static String replaceCharacterByMapping(final String originalStr) {
        String targetStr = originalStr;
        int len = originalStr.length();
        for (int a = 1; a <= len; a++) {
            int stepLen = a;
            int startIndex = 0;
            int endIndex = startIndex + stepLen;
            int count = len - a + 1;
            for (int i = 0; i < count; i++) {
                String stepCharacter = originalStr.substring(startIndex, endIndex);
                String res = trans.get(stepCharacter);
                if (res != null) {
                    targetStr = targetStr.replace(stepCharacter, res);
                }
                startIndex++;
                endIndex++;
            }
        }
        return targetStr;
    }

    public static void main(String[] args) {
        String originalQuery = "開始投射";

        long startTime = System.currentTimeMillis();
        replaceCharacterByMapping(originalQuery);
        System.out.println("cost:" + (System.currentTimeMillis() - startTime));
    }
}
