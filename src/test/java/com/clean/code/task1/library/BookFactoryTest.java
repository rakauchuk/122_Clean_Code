package com.clean.code.task1.library;

import com.clean.code.task1.library.factory.BookFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookFactoryTest {

    @Test
    void create_withValidId_returnsBook() {
        var book = BookFactory.create("ID1", "Title", "ISBN");
        assertEquals("ID1", book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals("ISBN", book.getIsbn());
    }

    @Test
    void create_withBlankId_throws() {
        assertThrows(IllegalArgumentException.class, () -> BookFactory.create(" ", "T", "I"));
        assertThrows(IllegalArgumentException.class, () -> BookFactory.create(null, "T", "I"));
    }
}
