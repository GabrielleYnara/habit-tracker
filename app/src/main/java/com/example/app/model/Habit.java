package com.example.app.model;

public class Habit {
    private Long Id;
    private String name;
    private String trigger;
    private String outcome;
    private String routine;
    private User user;
    private Category category;

    public Habit() {
    }

    public Habit(Long id, String name, String trigger, String outcome, String routine) {
        Id = id;
        this.name = name;
        this.trigger = trigger;
        this.outcome = outcome;
        this.routine = routine;
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
