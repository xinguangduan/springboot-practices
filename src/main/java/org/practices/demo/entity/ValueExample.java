package org.practices.demo.entity;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;

@Value
public class ValueExample {
    String name;
    @With(AccessLevel.PACKAGE)
    @NonFinal
    int age;
    double score;
    protected String[] tags;

    @ToString(includeFieldNames = true)
    @Value(staticConstructor = "of")
    public static class Exercise<T> {
        String name;
        T value;
    }

    public static void main(String[] args) {
        ValueExample example = new ValueExample("value", 1, 0.5, null);
    }
}
