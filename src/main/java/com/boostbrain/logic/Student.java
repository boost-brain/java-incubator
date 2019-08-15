package com.boostbrain.logic;

/*
Не уверен, надо ли сюда @Component
 */
public class Student {
    private long id;
    private String name;
    private String email;
    private String hours;

    public Student(String name, String email, String hours) {
        this.name = name;
        this.email = email;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
