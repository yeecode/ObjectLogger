package com.github.yeecode.objectLogger.ApplicationDemo.model;

import com.github.yeecode.objectLogger.client.annotation.LogTag;

public class Student extends Person {
    @LogTag(name = "School Name")
    private String schoolName;
    private Integer grade;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
