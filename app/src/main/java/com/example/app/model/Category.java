package com.example.app.model;

public class Category {
    private Long Id;
    private String name;
    private String description;
    private User user;
    private List<Habit> habitList;

    public Category() {
    }

    public Category(Long id, String name, String description) {
        Id = id;
        this.name = name;
        this.description = description;
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
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
