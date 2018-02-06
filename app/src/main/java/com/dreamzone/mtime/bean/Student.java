package com.dreamzone.mtime.bean;

import java.io.Serializable;

/**
 * @author bohe
 * @ClassName: Student
 * @Description:
 * @date 2017/11/29 下午10:41
 */
public class Student implements Serializable {
    private final static long serialVersionUID = 1L;
    private String name;
    private char sex;
    private int year;
    public int height;
    public int weight;

    public Student(){}

    public Student(String name, char sex, int year) {
        this.name = name;
        this.sex = sex;
        this.year = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return this.name;
    }

    public char getSex() {
        return this.sex;
    }

    public int getYear() {
        return this.year;
    }
}