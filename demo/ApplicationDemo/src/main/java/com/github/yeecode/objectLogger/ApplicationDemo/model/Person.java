package com.github.yeecode.objectLogger.ApplicationDemo.model;

import com.github.yeecode.objectLogger.client.annotation.LogTag;

public class Person {
    private Integer id;
    private String name;
    @LogTag(name = "Age")
    private int age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
