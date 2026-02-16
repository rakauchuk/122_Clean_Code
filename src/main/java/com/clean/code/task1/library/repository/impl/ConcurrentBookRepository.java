package com.clean.code.task1.library.repository.impl;

import com.clean.code.task1.library.model.Book;
import com.clean.code.task1.library.model.BookStatus;
import com.clean.code.task1.library.repository.BookRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of BookRepository.
 */
public final class ConcurrentBookRepository implements BookRepository {

    private final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, BookStatus> statusByBookId = new ConcurrentHashMap<>();

    @Override
    public void put(Book book, BookStatus status) {
        books.put(book.getId(), book);
        statusByBookId.put(book.getId(), status);
    }

    @Override
    public Optional<BookStatus> getStatus(String bookId) {
        return Optional.ofNullable(statusByBookId.get(bookId));
    }

    @Override
    public Optional<Book> findById(String bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    @Override
    public void remove(String bookId) {
        books.remove(bookId);
        statusByBookId.remove(bookId);
    }
}
