package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate a sequence of unused Long integer value
    @Column
    private Long Id;
    @Column(unique = true) //validation on SQL side
    private String emailAddress;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //doesn't allow reading it
    private String password;
    @OneToOne(cascade = CascadeType.ALL) //when loading user, load profile as well
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @OneToMany(mappedBy = "user", orphanRemoval = true) //if deleted, deletes associated records as well
    @LazyCollection(LazyCollectionOption.FALSE) //loads associated categories when loading the user
    private List<Category> categoryList;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
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
