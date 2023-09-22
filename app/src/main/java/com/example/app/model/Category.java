package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="categories")
public class Category {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate sequence of unused values
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @JsonIgnore // prevents stack overflow
    @ManyToOne //Many categories belong to the same user
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "category", orphanRemoval = true)//if deleted, deletes associated records as well
    @LazyCollection(LazyCollectionOption.FALSE) //loads associated habits when loading the category
    private List<Habit> habitList;

    public Category() {
    }

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Habit> getHabitList() {
        return habitList;
    }

    public void setHabitList(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
