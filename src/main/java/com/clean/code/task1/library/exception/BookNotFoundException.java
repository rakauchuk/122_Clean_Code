package com.clean.code.task1.library.exception;

/**
 * Thrown when a book is not found in the registry.
 */
public class BookNotFoundException extends LibraryException {

    public BookNotFoundException(String bookId) {
        super("Book not found: " + bookId);
    }
}
