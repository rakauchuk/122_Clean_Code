package com.clean.code.task1.library.observer;

/**
 * Listener for book-related events (Observer pattern).
 */
public interface BookEventListener {

    void onEvent(BookEvent event);
}
