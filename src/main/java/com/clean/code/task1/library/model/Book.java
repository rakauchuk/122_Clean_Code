package com.clean.code.task1.library.model;

import java.util.Objects;

/**
 * Represents a book entity in the library.
 * Immutable value object for the core book identity and metadata.
 */
public final class Book {

    private final String id;
    private final String title;
    private final String isbn;

    public Book(String id, String title, String isbn) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Book id must not be null or blank");
        }
        this.id = id.trim();
        this.title = title != null ? title.trim() : "";
        this.isbn = isbn != null ? isbn.trim() : "";
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
