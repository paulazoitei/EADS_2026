# Library Management System

## About the project

For this homework I created a small library management system using Spring Boot. The application works with members, books, borrowing and returning books.

The project is located in the `org.example.week3.library` package.

## What I implemented

I created two model classes:

- `Member`, which contains the member id, name, role and the books borrowed by that member;
- `Book`, which contains the book id, title and availability status.

The data is stored in memory. `MemberRepository` and `BookRepository` use simple Java lists instead of a database.

The application has two borrowing policies. `FixedLimitBorrowingPolicy` allows every member to borrow up to three books. `RoleBasedBorrowingPolicy` uses the member role: students can borrow two books, regular members three books and premium members five books.

The policy is selected from `application.properties`:

```properties
library.borrowing-policy=role
```

If I change the value to `fixed`, Spring injects the fixed limit policy instead. This is done with `@ConditionalOnProperty`.

The `BorrowingService` contains the main operations for borrowing and returning books. It uses constructor injection for the repositories, the selected borrowing policy, the event publisher and the configuration properties.

I also added a custom configuration property for the event threshold:

```properties
library.event.borrowed-books-threshold=2
```

This value is read by `LibraryProperties` using `@ConfigurationProperties`.

## AOP and exceptions

The `BorrowingAspect` is used for three things.

Before a book is borrowed, it checks that the member exists, the book exists, the book is available and the member has not reached the borrowing limit. If something is wrong, a custom exception is thrown.

The aspect also logs every borrow and return operation. The log contains the method name, member name, book title and execution time.

It also catches exceptions during borrowing and prints the failed operation and the error message. The custom exceptions are stored in the `exception` package.

## Events

When a member reaches the configured number of borrowed books, `BorrowingService` publishes a `MemberBorrowedManyBooksEvent`.

The `MemberBorrowedManyBooksListener` listens for this event using `@EventListener` and prints a message with the member name and the number of borrowed books.

## Testing

I tested the application using a `CommandLineRunner` in `Week3Application`.

The test does the following:

1. Ion Popescu borrows `Clean Code`.
2. He borrows `Effective Java`.
3. The event is triggered after he reaches two borrowed books.
4. He returns `Clean Code`.
5. He borrows it again.
6. The application tries to borrow the same book again and the expected exception is thrown.

The output confirmed that the selected policy, validation, logging, event listener, return operation and exception handling work correctly.

Example output:

```text
Selected policy: Role-based policy
VALIDATION ASPECT: borrowing request is valid
Ion Popescu borrowed Clean Code using Role-based policy
EVENT LISTENER: Member Ion Popescu has borrowed 2 books.
Ion Popescu returned Clean Code
EXCEPTION ASPECT: operation=borrowBook, error=Book with id 1 is not available
MAIN: operation failed - Book with id 1 is not available
```
