package com.clean.code.task1.library.service;

import com.clean.code.task1.library.model.Reservation;
import com.clean.code.task1.library.observer.BookEvent;
import com.clean.code.task1.library.observer.BookEventListener;
import com.clean.code.task1.library.observer.BookReturnedEvent;
import com.clean.code.task1.library.repository.ReservationRepository;

import java.util.List;

/**
 * Observer for book-returned events (OCP: e.g. notify first in reservation queue).
 * Status is already updated by LibraryService; this allows future extensions without changing core.
 */
public final class ReservationFulfillmentListener implements BookEventListener {

    private final ReservationRepository reservationRepository;

    public ReservationFulfillmentListener(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void onEvent(BookEvent event) {
        if (event instanceof BookReturnedEvent returned) {
            List<Reservation> queue = reservationRepository.findByBookIdOrderByCreatedAt(returned.getBookId());
            if (!queue.isEmpty()) {
                // Extension point: e.g. notify queue.get(0).getUserId() that the book is ready
            }
        }
    }
}
