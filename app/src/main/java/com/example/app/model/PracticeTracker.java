package com.example.app.model;

import java.time.LocalDate;

public class PracticeTracker {
    private Long id;
    private boolean done;
    private LocalDate date;
    private Habit habit;

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

    @Override
    public String toString() {
        return "PracticeTracker{" +
                "id=" + id +
                ", done=" + done +
                ", date=" + date +
                ", habit=" + habit +
                '}';
    }
}
