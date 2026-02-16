package com.clean.code.task1.library.repository;

import com.clean.code.task1.library.model.Book;
import com.clean.code.task1.library.model.BookStatus;

import java.util.Optional;

/**
 * Repository for book inventory and status (SRP: data access only).
 */
public interface BookRepository {

    /**
     * Registers or updates a book and its status.
     */
    void put(Book book, BookStatus status);

    /**
     * Returns the current status of a book, or empty if not registered.
     */
    Optional<BookStatus> getStatus(String bookId);

    /**
     * Returns the book by id if registered.
     */
    Optional<Book> findById(String bookId);

    /**
     * Removes status for a book (e.g. when removing from catalog).
     */
    void remove(String bookId);
}
