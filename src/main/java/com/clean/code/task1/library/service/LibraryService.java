package com.clean.code.task1.library.service;

import com.clean.code.task1.library.exception.BookNotCheckedOutException;
import com.clean.code.task1.library.exception.BookNotFoundException;
import com.clean.code.task1.library.exception.BookUnavailableException;
import com.clean.code.task1.library.model.Book;
import com.clean.code.task1.library.model.BookStatus;
import com.clean.code.task1.library.model.Loan;
import com.clean.code.task1.library.model.Reservation;
import com.clean.code.task1.library.observer.BookCheckedOutEvent;
import com.clean.code.task1.library.observer.BookEventPublisher;
import com.clean.code.task1.library.observer.BookReturnedEvent;
import com.clean.code.task1.library.repository.BookRepository;
import com.clean.code.task1.library.repository.LoanRepository;
import com.clean.code.task1.library.repository.ReservationRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Main facade for library operations: checkout, return, reserve.
 * Coordinates repositories and events (SRP: orchestration only).
 */
public final class LibraryService {

    private static final int DEFAULT_LOAN_DAYS = 14;

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final BookEventPublisher eventPublisher;
    private final OverdueService overdueService;
    private final int loanDays;

    public LibraryService(BookRepository bookRepository,
                          LoanRepository loanRepository,
                          ReservationRepository reservationRepository,
                          BookEventPublisher eventPublisher,
                          OverdueService overdueService) {
        this(bookRepository, loanRepository, reservationRepository, eventPublisher, overdueService, DEFAULT_LOAN_DAYS);
    }

    public LibraryService(BookRepository bookRepository,
                          LoanRepository loanRepository,
                          ReservationRepository reservationRepository,
                          BookEventPublisher eventPublisher,
                          OverdueService overdueService,
                          int loanDays) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
        this.overdueService = overdueService;
        this.loanDays = loanDays;
    }

    /**
     * Checks out a book to a user if it is available.
     *
     * @throws BookNotFoundException   if the book is not in the catalog
     * @throws BookUnavailableException if the book is loaned or reserved by someone else
     */
    public void checkOutBook(String bookId, String userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BookStatus status = bookRepository.getStatus(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (status == BookStatus.LOANED) {
            throw new BookUnavailableException(bookId);
        }
        if (status == BookStatus.RESERVED) {
            List<Reservation> queue = reservationRepository.findByBookIdOrderByCreatedAt(bookId);
            boolean isFirstReserver = !queue.isEmpty() && queue.get(0).getUserId().equals(userId);
            if (!isFirstReserver) {
                throw new BookUnavailableException(bookId);
            }
            reservationRepository.removeByBookIdAndUserId(bookId, userId);
        }

        Instant now = Instant.now();
        Instant dueAt = now.plus(loanDays, ChronoUnit.DAYS);
        Loan loan = new Loan(bookId, userId, now, dueAt);
        loanRepository.save(loan);
        bookRepository.put(book, BookStatus.LOANED);
        eventPublisher.publish(new BookCheckedOutEvent(bookId, userId));
    }

    /**
     * Returns a book. Fires BookReturnedEvent so reservation fulfillment can run.
     *
     * @throws BookNotCheckedOutException if the book was not checked out
     */
    public void returnBook(String bookId) {
        Loan loan = loanRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotCheckedOutException(bookId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        loanRepository.removeByBookId(bookId);
        boolean hasReservations = !reservationRepository.findByBookIdOrderByCreatedAt(bookId).isEmpty();
        bookRepository.put(book, hasReservations ? BookStatus.RESERVED : BookStatus.AVAILABLE);
        eventPublisher.publish(new BookReturnedEvent(bookId, loan.getUserId()));
    }

    /**
     * Reserves a book when it is currently loaned. Does not apply when book is available.
     *
     * @throws BookNotFoundException if the book is not in the catalog
     * @throws BookUnavailableException if the book is available (no need to reserve) or already reserved
     */
    public void reserveBook(String bookId, String userId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BookStatus status = bookRepository.getStatus(bookId).orElse(BookStatus.AVAILABLE);
        if (status == BookStatus.AVAILABLE) {
            throw new BookUnavailableException(bookId + " is available; checkout instead of reserve.");
        }

        Reservation reservation = new Reservation(bookId, userId, Instant.now());
        reservationRepository.add(reservation);
        if (status != BookStatus.RESERVED) {
            bookRepository.put(bookRepository.findById(bookId).orElseThrow(), BookStatus.RESERVED);
        }
    }

    /**
     * Returns overdue loans and the total fine amount at the given time.
     */
    public List<Loan> getOverdueLoans(Instant asOf) {
        return overdueService.getOverdueLoans(asOf);
    }

    /**
     * Fine in minor units (e.g. cents) for the given loan at the given time.
     */
    public long getOverdueFine(Loan loan, Instant asOf) {
        return overdueService.getFineFor(loan, asOf);
    }
}
