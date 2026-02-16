package com.clean.code.task1.library.strategy;

import com.clean.code.task1.library.model.Loan;

import java.time.Duration;
import java.time.Instant;

/**
 * Simple fine: fixed amount per day overdue.
 */
public final class FlatDailyFineStrategy implements FineCalculationStrategy {

    private final long centsPerDay;

    public FlatDailyFineStrategy(long centsPerDay) {
        if (centsPerDay < 0) {
            throw new IllegalArgumentException("centsPerDay must be non-negative");
        }
        this.centsPerDay = centsPerDay;
    }

    @Override
    public long calculateFine(Loan loan, Instant asOf) {
        if (!loan.isOverdue(asOf)) {
            return 0;
        }
        long days = Duration.between(loan.getDueAt(), asOf).toDays();
        return days * centsPerDay;
    }
}
