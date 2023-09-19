package com.example.app.model;

import java.util.List;

public class User {
    private Long Id;
    private String emailAddress;
    private String password;
    private Profile profile;

    private List<Category> categoryList;
    private List<Habit> habitList;

    public User() {
    }

    public User(Long id, String emailAddress, String password) {
        Id = id;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Habit> getHabitList() {
        return habitList;
    }

    public void setHabitList(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", emailAddress='" + emailAddress + '\'' +
                ", profile=" + profile +
                '}';
    }
}
