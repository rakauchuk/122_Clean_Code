package com.clean.code.task1.library.strategy;

import com.clean.code.task1.library.model.Loan;

import java.time.Instant;

/**
 * Strategy for calculating overdue fines (Strategy pattern, OCP).
 */
public interface FineCalculationStrategy {

    /**
     * Calculates the fine amount for an overdue loan at the given time.
     *
     * @param loan the overdue loan
     * @param asOf time at which to compute the fine
     * @return fine amount in minor units (e.g. cents)
     */
    long calculateFine(Loan loan, Instant asOf);
}
