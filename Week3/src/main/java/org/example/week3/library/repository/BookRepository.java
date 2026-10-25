package org.example.week3.library.repository;

import org.example.week3.library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        books.add(new Book(1L, "Clean Code"));
        books.add(new Book(2L, "Effective Java"));
        books.add(new Book(3L, "Spring in Action"));
        books.add(new Book(4L, "Design Patterns"));
    }

    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public List<Book> findAll() {
        return books;
    }
}