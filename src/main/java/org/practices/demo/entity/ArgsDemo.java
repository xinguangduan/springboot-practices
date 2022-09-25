package org.practices.demo.entity;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArgsDemo {

    private final String name;

    private String address;


    public static void main(String[] args) {
        RequiredArgsDemo requiredArgsDemo = RequiredArgsDemo.me("sss");
        System.out.println(requiredArgsDemo);
    }
}
