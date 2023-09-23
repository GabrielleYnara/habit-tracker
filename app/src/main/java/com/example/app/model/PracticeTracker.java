package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "practices")
public class PracticeTracker {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate sequence of unused values
    @Column
    private Long id;
    @Column
    private boolean done;
    @Column
    private LocalDate date;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PracticeTracker() {
    }

    public PracticeTracker(Long id, boolean done, LocalDate date, Habit habit) {
        this.id = id;
        this.done = done;
        this.date = date;
        this.habit = habit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PracticeTracker{" +
                "id=" + id +
                ", done=" + done +
                ", date=" + date +
                ", habit=" + habit +
                ", user=" + user +
                '}';
    }
}
