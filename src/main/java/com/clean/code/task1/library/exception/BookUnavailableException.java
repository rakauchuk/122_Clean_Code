package com.clean.code.task1.library.exception;

/**
 * Thrown when a book cannot be checked out (e.g. already loaned).
 */
public class BookUnavailableException extends LibraryException {

    public BookUnavailableException(String bookId) {
        super("Book is currently unavailable: " + bookId);
    }
}
