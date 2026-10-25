package org.example.week3.library.service;

import org.example.week3.library.event.MemberBorrowedManyBooksEvent;
import org.example.week3.library.exception.BookNotFoundException;
import org.example.week3.library.exception.MemberNotFoundException;
import org.example.week3.library.model.Book;
import org.example.week3.library.model.Member;
import org.example.week3.library.policy.BorrowingPolicy;
import org.example.week3.library.repository.BookRepository;
import org.example.week3.library.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.example.week3.library.config.LibraryProperties;

@Service
public class BorrowingService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BorrowingPolicy borrowingPolicy;
    private final ApplicationEventPublisher eventPublisher;
    private final LibraryProperties libraryProperties;



    public BorrowingService(
            MemberRepository memberRepository,
            BookRepository bookRepository,
            BorrowingPolicy borrowingPolicy,
            ApplicationEventPublisher eventPublisher,
            LibraryProperties libraryProperties) {


        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.borrowingPolicy = borrowingPolicy;
        this.eventPublisher = eventPublisher;
        this.libraryProperties = libraryProperties;
    }

    public void borrowBook(Long memberId, Long bookId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        book.borrow();
        member.borrowBook(book.getId());

        System.out.println(
                member.getName()
                        + " borrowed "
                        + book.getTitle()
                        + " using "
                        + borrowingPolicy.getPolicyName()
        );

        if (member.getBorrowedBooksCount()
                >= libraryProperties
                .getEvent()
                .getBorrowedBooksThreshold()) {

            eventPublisher.publishEvent(
                    new MemberBorrowedManyBooksEvent(
                            this,
                            member,
                            member.getBorrowedBooksCount()
                    )
            );
        }
    }

    public void returnBook(Long memberId, Long bookId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        book.makeAvailable();
        member.returnBook(book.getId());

        System.out.println(
                member.getName()
                        + " returned "
                        + book.getTitle()
        );
    }

    public BorrowingPolicy getBorrowingPolicy() {
        return borrowingPolicy;
    }
}