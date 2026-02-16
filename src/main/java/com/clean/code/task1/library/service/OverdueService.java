package com.clean.code.task1.library.service;

import com.clean.code.task1.library.model.Loan;
import com.clean.code.task1.library.repository.LoanRepository;
import com.clean.code.task1.library.strategy.FineCalculationStrategy;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles overdue loan detection and fine calculation (SRP).
 */
public final class OverdueService {

    private final LoanRepository loanRepository;
    private final FineCalculationStrategy fineStrategy;

    public OverdueService(LoanRepository loanRepository, FineCalculationStrategy fineStrategy) {
        this.loanRepository = loanRepository;
        this.fineStrategy = fineStrategy;
    }

    /**
     * Returns all loans that are overdue at the given time.
     */
    public List<Loan> getOverdueLoans(Instant asOf) {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.isOverdue(asOf))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the fine for a specific loan at the given time.
     */
    public long getFineFor(Loan loan, Instant asOf) {
        return fineStrategy.calculateFine(loan, asOf);
    }

    /**
     * Total fines for all overdue loans at the given time.
     */
    public long getTotalOverdueFines(Instant asOf) {
        return getOverdueLoans(asOf).stream()
                .mapToLong(loan -> fineStrategy.calculateFine(loan, asOf))
                .sum();
    }
}
