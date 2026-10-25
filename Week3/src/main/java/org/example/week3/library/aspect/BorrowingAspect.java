package org.example.week3.library.aspect;

import org.example.week3.library.exception.BookNotAvailableException;
import org.example.week3.library.exception.BookNotFoundException;
import org.example.week3.library.exception.BorrowingLimitExceededException;
import org.example.week3.library.exception.MemberNotFoundException;
import org.example.week3.library.model.Book;
import org.example.week3.library.model.Member;
import org.example.week3.library.policy.BorrowingPolicy;
import org.example.week3.library.repository.BookRepository;
import org.example.week3.library.repository.MemberRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BorrowingAspect {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BorrowingPolicy borrowingPolicy;

    public BorrowingAspect(
            MemberRepository memberRepository,
            BookRepository bookRepository,
            BorrowingPolicy borrowingPolicy) {

        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.borrowingPolicy = borrowingPolicy;
    }

    @Before(
            "execution(* org.example.week3.library.service.BorrowingService.borrowBook(..)) "
                    + "&& args(memberId, bookId)"
    )
    public void validateBorrowing(Long memberId, Long bookId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException(bookId);
        }

        if (!borrowingPolicy.canBorrow(member)) {
            throw new BorrowingLimitExceededException(
                    "Member " + member.getName()
                            + " exceeded the borrowing limit of "
                            + borrowingPolicy.getMaximumBooks(member)
            );
        }

        System.out.println(
                "VALIDATION ASPECT: borrowing request is valid"
        );
    }

    @Around(
            "execution(* org.example.week3.library.service.BorrowingService.borrowBook(..)) "
                    + "|| execution(* org.example.week3.library.service.BorrowingService.returnBook(..))"
    )
    public Object logBorrowingOperation(
            ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();

        } catch (Throwable exception) {

            System.out.println(
                    "EXCEPTION ASPECT: operation="
                            + joinPoint.getSignature().getName()
                            + ", error="
                            + exception.getMessage()
            );

            throw exception;

        } finally {

            long executionTime =
                    System.currentTimeMillis() - startTime;

            Object[] arguments = joinPoint.getArgs();

            Long memberId = (Long) arguments[0];
            Long bookId = (Long) arguments[1];

            String memberName = memberRepository.findById(memberId)
                    .map(Member::getName)
                    .orElse("Unknown member");

            String bookTitle = bookRepository.findById(bookId)
                    .map(Book::getTitle)
                    .orElse("Unknown book");

            System.out.println(
                    "LOG ASPECT: method="
                            + joinPoint.getSignature().getName()
                            + ", member="
                            + memberName
                            + ", book="
                            + bookTitle
                            + ", executionTime="
                            + executionTime
                            + " ms"
            );
        }
    }


}