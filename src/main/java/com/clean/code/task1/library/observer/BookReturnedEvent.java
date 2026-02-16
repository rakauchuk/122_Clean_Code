package com.clean.code.task1.library.observer;

/**
 * Fired when a book is returned to the library.
 */
public record BookReturnedEvent(String bookId, String returnedByUserId) implements BookEvent {

    @Override
    public String getBookId() {
        return bookId;
    }
}
