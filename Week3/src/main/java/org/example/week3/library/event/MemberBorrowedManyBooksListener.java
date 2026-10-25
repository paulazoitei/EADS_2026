package org.example.week3.library.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MemberBorrowedManyBooksListener {

    @EventListener
    public void handleMemberBorrowedManyBooks(
            MemberBorrowedManyBooksEvent event) {

        System.out.println(
                "EVENT LISTENER: Member "
                        + event.getMember().getName()
                        + " has borrowed "
                        + event.getBorrowedBooksCount()
                        + " books."
        );
    }
}