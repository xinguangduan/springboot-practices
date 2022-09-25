package org.practices.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@Builder
@AllArgsConstructor
public class ToStringDemo {

    @ToString.Include
    private String name;
    private String address;
    @ToString.Exclude
    private String url;

    public static void main(String[] args) {
        var demo = ToStringDemo.builder()
                .name("zhangsan")
                .address("北京昌平")
                .url("https://www.baidu.com")
                .build();
        System.out.println(demo.toString());
    }
}
