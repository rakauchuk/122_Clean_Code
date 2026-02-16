package com.clean.code.task1.library.observer;

/**
 * Domain events for observer pattern (OCP: new listeners without changing core).
 */
public sealed interface BookEvent permits BookReturnedEvent, BookCheckedOutEvent {

    String getBookId();
}
