package com.clean.code.task1.library.factory;

import com.clean.code.task1.library.model.Book;

/**
 * Factory for creating Book instances (Factory pattern, validation in one place).
 */
public final class BookFactory {

    /**
     * Creates a valid Book from raw input.
     *
     * @param id    non-null, non-blank
     * @param title optional
     * @param isbn  optional
     */
    public static Book create(String id, String title, String isbn) {
        return new Book(
                sanitize(id, "id"),
                title != null ? title : "",
                isbn != null ? isbn : ""
        );
    }

    public static Book create(String id) {
        return create(id, null, null);
    }

    private static String sanitize(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Book " + fieldName + " must not be null or blank");
        }
        return value.trim();
    }
}
