package org.practices.demo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;


@AllArgsConstructor
@Log
@Getter
@Setter
public class SetterDemo {
    private String m_test;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String m_noneSetter;

    public static void main(String[] args) {
        SetterDemo setterDemo = new SetterDemo("sss","sssss");

    }
}
