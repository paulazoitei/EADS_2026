package org.example.week3.library.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long bookId) {
        super("Book with id " + bookId + " was not found");
    }
}