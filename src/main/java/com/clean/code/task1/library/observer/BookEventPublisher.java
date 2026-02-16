package com.clean.code.task1.library.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple in-process event publisher for book events.
 */
public final class BookEventPublisher {

    private final List<BookEventListener> listeners = new ArrayList<>();

    public void subscribe(BookEventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void publish(BookEvent event) {
        for (BookEventListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                // Log and continue so one failing listener doesn't break others
            }
        }
    }
}
