package com.clean.code.task1.library.repository;

import com.clean.code.task1.library.model.Loan;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository for active loans (SRP).
 */
public interface LoanRepository {

    void save(Loan loan);

    Optional<Loan> findByBookId(String bookId);

    void removeByBookId(String bookId);

    Collection<Loan> findAll();
}
