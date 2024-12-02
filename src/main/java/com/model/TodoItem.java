package com.model;

public class TodoItem {

    private Integer id;
    private String text;
    private boolean done;

    public TodoItem(Integer id, String text, boolean done) {
        this.id = id;
        this.text = text;
        this.done = done;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }
}
