package org.example.week3.library.event;

import org.example.week3.library.model.Member;
import org.springframework.context.ApplicationEvent;

public class MemberBorrowedManyBooksEvent extends ApplicationEvent {

    private final Member member;
    private final int borrowedBooksCount;

    public MemberBorrowedManyBooksEvent(
            Object source,
            Member member,
            int borrowedBooksCount) {

        super(source);
        this.member = member;
        this.borrowedBooksCount = borrowedBooksCount;
    }

    public Member getMember() {
        return member;
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }
}