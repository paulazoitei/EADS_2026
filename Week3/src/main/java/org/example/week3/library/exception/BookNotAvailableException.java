package org.example.week3.library.exception;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(Long bookId) {
        super("Book with id " + bookId + " is not available");
    }
}