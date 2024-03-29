package org.practices.demo.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data(staticConstructor = "of")
public class DataDemo {
    private final String name;
    @Setter(AccessLevel.PACKAGE)
    private int age;
    private double score;
    private String[] tags;

    @ToString(includeFieldNames = true)
    @Data(staticConstructor = "of")
    public static class Exercise<T> {
        private final String name;
        private final T value;
    }

    public static void main(String[] args) {
        DataDemo.of("name");
    }
}
