package org.example.week3.library.model;

public class Book {

    private Long id;
    private String title;
    private boolean available;

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
        this.available = true;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrow() {
        this.available = false;
    }

    public void makeAvailable() {
        this.available = true;
    }
}