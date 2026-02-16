package com.clean.code.task1.library.observer;

/**
 * Fired when a book is checked out.
 */
public record BookCheckedOutEvent(String bookId, String userId) implements BookEvent {

    @Override
    public String getBookId() {
        return bookId;
    }
}
