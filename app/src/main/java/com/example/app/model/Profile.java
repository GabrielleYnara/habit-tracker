package com.example.app.model;

public class Profile {
    private Long Id;
    private String firstName;
    private String lastName;
    private String bio;
    private User user;

    public Profile() {
    }

    public Profile(Long id, String firstName, String lastName, String bio, User user) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", user=" + user +
                '}';
    }
}
