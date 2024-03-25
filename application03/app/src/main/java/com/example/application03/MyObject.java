package com.example.application03;

import java.io.Serializable;

public class MyObject implements Serializable {
    private String name;
    private int age;
    public MyObject(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}