package com.clean.code.task1.library.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a user's reservation for a currently loaned book.
 */
public final class Reservation {

    private final String bookId;
    private final String userId;
    private final Instant createdAt;

    public Reservation(String bookId, String userId, Instant createdAt) {
        this.bookId = Objects.requireNonNull(bookId, "bookId");
        this.userId = Objects.requireNonNull(userId, "userId");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
