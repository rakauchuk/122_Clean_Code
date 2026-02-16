package com.clean.code.task1.library;

import com.clean.code.task1.library.factory.BookFactory;
import com.clean.code.task1.library.model.BookStatus;
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

/**
 * Wiring and demo for the refactored library system.
 */
public final class LibraryApp {

    public static void main(String[] args) {
        BookRepository bookRepo = new ConcurrentBookRepository();
        LoanRepository loanRepo = new ConcurrentLoanRepository();
        ReservationRepository reservationRepo = new ConcurrentReservationRepository();
        BookEventPublisher publisher = new BookEventPublisher();
        FineCalculationStrategy fineStrategy = new FlatDailyFineStrategy(10);
        OverdueService overdueService = new OverdueService(loanRepo, fineStrategy);

        publisher.subscribe(new ReservationFulfillmentListener(reservationRepo));

        LibraryService library = new LibraryService(bookRepo, loanRepo, reservationRepo, publisher, overdueService);

        bookRepo.put(BookFactory.create("BK001", "Clean Code", "978-0132350884"), BookStatus.AVAILABLE);
        bookRepo.put(BookFactory.create("BK002", "Effective Java", "978-0134685991"), BookStatus.AVAILABLE);

        library.checkOutBook("BK001", "USR001");
        library.returnBook("BK001");
    }
}
