package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "habits")
public class Habit {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate sequence of unused values
    @Column
    private Long Id;
    @Column
    private String name;
    @Column
    private String trigger;
    @Column
    private String outcome;
    @Column
    private String routine;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore //prevents stack overflow
    @ManyToOne //Many habits belong to the same category
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "habit", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PracticeTracker> practiceTrackerList;


    public Habit() {
    }

    public Habit(Long id, String name, String trigger, String outcome, String routine, Category category) {
        Id = id;
        this.name = name;
        this.trigger = trigger;
        this.outcome = outcome;
        this.routine = routine;
        this.category = category;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", trigger='" + trigger + '\'' +
                ", outcome='" + outcome + '\'' +
                ", routine='" + routine + '\'' +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
