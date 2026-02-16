package com.clean.code.task1.library.exception;

/**
 * Thrown when attempting to return a book that was not checked out.
 */
public class BookNotCheckedOutException extends LibraryException {

    public BookNotCheckedOutException(String bookId) {
        super("This book was not checked out: " + bookId);
    }
}
