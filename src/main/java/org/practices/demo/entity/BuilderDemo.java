package org.practices.demo.entity;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Builder
@ToString
@Slf4j
public class BuilderDemo {
    @Builder.Default
    private long created = System.currentTimeMillis();
    private String name;
    private int age;
    @Singular
    private Set<String> occupations;

    public static void main(String[] args) {
        BuilderDemo builderExample = BuilderDemo.builder().age(1).name("test").build();
        log.info("builderExample.toString() : {}", builderExample.toString());
    }
}
