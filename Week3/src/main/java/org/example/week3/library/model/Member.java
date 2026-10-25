package org.example.week3.library.model;

import java.util.ArrayList;
import java.util.List;

public class Member {

    private Long id;
    private String name;
    private MemberRole role;
    private List<Long> borrowedBookIds = new ArrayList<>();

    public Member(Long id, String name, MemberRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MemberRole getRole() {
        return role;
    }

    public List<Long> getBorrowedBookIds() {
        return borrowedBookIds;
    }

    public int getBorrowedBooksCount() {
        return borrowedBookIds.size();
    }

    public void borrowBook(Long bookId) {
        borrowedBookIds.add(bookId);
    }

    public void returnBook(Long bookId) {
        borrowedBookIds.remove(bookId);
    }
}