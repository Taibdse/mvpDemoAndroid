package com.example.home.mvpdemo2.model.todo;

public class Todo {
    private String name, description;
    int id, level;

    public Todo(int id, String name, String description, int level) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.level = level;
    }

    public Todo(){}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
