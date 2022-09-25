package org.practices.demo.entity;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "me")
public class RequiredArgsDemo {

    private final String name;

    private String address;


    public static void main(String[] args) {
        RequiredArgsDemo me = new RequiredArgsDemo("ss");

        System.out.println(RequiredArgsDemo.me("szhang").name);
    }
}
