package com.clean.code.task1.library.repository.impl;

import com.clean.code.task1.library.model.Reservation;
import com.clean.code.task1.library.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Thread-safe in-memory implementation of ReservationRepository.
 */
public final class ConcurrentReservationRepository implements ReservationRepository {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<Reservation>> byBookId = new ConcurrentHashMap<>();

    @Override
    public void add(Reservation reservation) {
        byBookId.computeIfAbsent(reservation.getBookId(), k -> new CopyOnWriteArrayList<>()).add(reservation);
    }

    @Override
    public List<Reservation> findByBookIdOrderByCreatedAt(String bookId) {
        List<Reservation> list = byBookId.getOrDefault(bookId, new CopyOnWriteArrayList<>());
        return list.stream()
                .sorted((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeByBookIdAndUserId(String bookId, String userId) {
        List<Reservation> list = byBookId.get(bookId);
        if (list != null) {
            list.removeIf(r -> r.getUserId().equals(userId));
        }
    }

    @Override
    public void removeAllByBookId(String bookId) {
        byBookId.remove(bookId);
    }
}
