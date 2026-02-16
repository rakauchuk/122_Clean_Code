package com.clean.code.task1.library.repository.impl;

import com.clean.code.task1.library.model.Loan;
import com.clean.code.task1.library.repository.LoanRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of LoanRepository.
 */
public final class ConcurrentLoanRepository implements LoanRepository {

    private final ConcurrentHashMap<String, Loan> loanByBookId = new ConcurrentHashMap<>();

    @Override
    public void save(Loan loan) {
        loanByBookId.put(loan.getBookId(), loan);
    }

    @Override
    public Optional<Loan> findByBookId(String bookId) {
        return Optional.ofNullable(loanByBookId.get(bookId));
    }

    @Override
    public void removeByBookId(String bookId) {
        loanByBookId.remove(bookId);
    }

    @Override
    public Collection<Loan> findAll() {
        return loanByBookId.values();
    }
}
