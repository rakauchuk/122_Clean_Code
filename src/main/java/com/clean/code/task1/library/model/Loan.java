package com.clean.code.task1.library.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents an active loan of a book to a user.
 */
public final class Loan {

    private final String bookId;
    private final String userId;
    private final Instant borrowedAt;
    private final Instant dueAt;

    public Loan(String bookId, String userId, Instant borrowedAt, Instant dueAt) {
        this.bookId = Objects.requireNonNull(bookId, "bookId");
        this.userId = Objects.requireNonNull(userId, "userId");
        this.borrowedAt = Objects.requireNonNull(borrowedAt, "borrowedAt");
        this.dueAt = Objects.requireNonNull(dueAt, "dueAt");
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public Instant getBorrowedAt() {
        return borrowedAt;
    }

    public Instant getDueAt() {
        return dueAt;
    }

    public boolean isOverdue(Instant now) {
        return now.isAfter(dueAt);
    }
}
