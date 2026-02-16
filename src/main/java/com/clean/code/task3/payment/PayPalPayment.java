package com.clean.code.task3.payment;

/**
 * PayPal payment. LSP: we do not throw UnsupportedOperationException;
 * instead, "account not linked to bank" is treated as failed validation
 * so processPayment is only called when the method can succeed.
 */
public class PayPalPayment extends PaymentMethod {

    private final String accountId;
    private final boolean linkedToBank;

    public PayPalPayment(String accountId, boolean linkedToBank) {
        this.accountId = accountId;
        this.linkedToBank = linkedToBank;
    }

    @Override
    public boolean validatePaymentDetails() {
        if (accountId == null || accountId.isBlank()) {
            return false;
        }
        if (!linkedToBank) {
            return false; // Previously caused UnsupportedOperationException in processPayment — now fails validation
        }
        return true;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (amount <= 0) {
            return PaymentResult.failure("Amount must be positive");
        }
        // Simulate: log in to PayPal and process; no throw
        return PaymentResult.ok();
    }
}
