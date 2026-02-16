package com.clean.code.task1.library.repository;

import com.clean.code.task1.library.model.Reservation;

import java.util.Collection;
import java.util.List;

/**
 * Repository for book reservations (SRP).
 */
public interface ReservationRepository {

    void add(Reservation reservation);

    List<Reservation> findByBookIdOrderByCreatedAt(String bookId);

    void removeByBookIdAndUserId(String bookId, String userId);

    void removeAllByBookId(String bookId);
}
