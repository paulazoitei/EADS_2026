package org.example.week3.library.exception;

public class BorrowingLimitExceededException extends RuntimeException {

    public BorrowingLimitExceededException(String message) {
        super(message);
    }
}