package com.clean.code.task1.library;

import com.clean.code.task1.library.exception.BookNotCheckedOutException;
import com.clean.code.task1.library.exception.BookNotFoundException;
import com.clean.code.task1.library.exception.BookUnavailableException;
import com.clean.code.task1.library.factory.BookFactory;
import com.clean.code.task1.library.model.BookStatus;
import com.clean.code.task1.library.model.Loan;
import com.clean.code.task1.library.observer.BookEventPublisher;
import com.clean.code.task1.library.repository.BookRepository;
import com.clean.code.task1.library.repository.LoanRepository;
import com.clean.code.task1.library.repository.ReservationRepository;
import com.clean.code.task1.library.repository.impl.ConcurrentBookRepository;
import com.clean.code.task1.library.repository.impl.ConcurrentLoanRepository;
import com.clean.code.task1.library.repository.impl.ConcurrentReservationRepository;
import com.clean.code.task1.library.service.LibraryService;
import com.clean.code.task1.library.service.OverdueService;
import com.clean.code.task1.library.service.ReservationFulfillmentListener;
import com.clean.code.task1.library.strategy.FlatDailyFineStrategy;
import com.clean.code.task1.library.strategy.FineCalculationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {

    private BookRepository bookRepo;
    private LoanRepository loanRepo;
    private ReservationRepository reservationRepo;
    private LibraryService library;

    @BeforeEach
    void setUp() {
        bookRepo = new ConcurrentBookRepository();
        loanRepo = new ConcurrentLoanRepository();
        reservationRepo = new ConcurrentReservationRepository();
        BookEventPublisher publisher = new BookEventPublisher();
        publisher.subscribe(new ReservationFulfillmentListener(reservationRepo));
        FineCalculationStrategy fineStrategy = new FlatDailyFineStrategy(10);
        OverdueService overdueService = new OverdueService(loanRepo, fineStrategy);
        library = new LibraryService(bookRepo, loanRepo, reservationRepo, publisher, overdueService);

        bookRepo.put(BookFactory.create("BK001", "Clean Code", "978-0132350884"), BookStatus.AVAILABLE);
        bookRepo.put(BookFactory.create("BK002", "Effective Java", "978-0134685991"), BookStatus.AVAILABLE);
    }

    @Test
    void checkoutAndReturn_success() {
        library.checkOutBook("BK001", "USR001");
        assertEquals(BookStatus.LOANED, bookRepo.getStatus("BK001").orElseThrow());
        assertTrue(loanRepo.findByBookId("BK001").isPresent());

        library.returnBook("BK001");
        assertEquals(BookStatus.AVAILABLE, bookRepo.getStatus("BK001").orElseThrow());
        assertTrue(loanRepo.findByBookId("BK001").isEmpty());
    }

    @Test
    void checkout_unknownBook_throws() {
        assertThrows(BookNotFoundException.class, () -> library.checkOutBook("UNKNOWN", "USR001"));
    }

    @Test
    void checkout_whenLoaned_throws() {
        library.checkOutBook("BK001", "USR001");
        assertThrows(BookUnavailableException.class, () -> library.checkOutBook("BK001", "USR002"));
    }

    @Test
    void return_whenNotCheckedOut_throws() {
        assertThrows(BookNotCheckedOutException.class, () -> library.returnBook("BK001"));
    }

    @Test
    void reserve_whenLoaned_addsReservation() {
        library.checkOutBook("BK001", "USR001");
        library.reserveBook("BK001", "USR002");
        assertFalse(reservationRepo.findByBookIdOrderByCreatedAt("BK001").isEmpty());
    }

    @Test
    void reserve_thenReturn_thenFirstInQueueCanCheckout() {
        library.checkOutBook("BK001", "USR001");
        library.reserveBook("BK001", "USR002");
        library.returnBook("BK001");
        assertEquals(BookStatus.RESERVED, bookRepo.getStatus("BK001").orElseThrow());
        library.checkOutBook("BK001", "USR002");
        assertEquals(BookStatus.LOANED, bookRepo.getStatus("BK001").orElseThrow());
    }

    @Test
    void overdueFine_calculatedCorrectly() {
        library.checkOutBook("BK001", "USR001");
        Loan loan = loanRepo.findByBookId("BK001").orElseThrow();
        Instant future = loan.getDueAt().plusSeconds(86400 * 3); // 3 days overdue
        long fine = library.getOverdueFine(loan, future);
        assertEquals(30, fine); // 10 cents/day * 3
    }
}
