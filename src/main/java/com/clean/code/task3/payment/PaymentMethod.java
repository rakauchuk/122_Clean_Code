package com.clean.code.task3.payment;

/**
 * Base type for payment methods. Subclasses must be substitutable (LSP):
 * validatePaymentDetails() and processPayment() must not throw unexpected exceptions
 * or violate contracts that callers rely on when using PaymentMethod.
 */
public abstract class PaymentMethod {

    /**
     * Validates that this payment method can be used (e.g. card valid, account linked).
     * Must not throw for invalid state — return false instead.
     */
    public abstract boolean validatePaymentDetails();

    /**
     * Processes the payment. Must not throw for business failures (e.g. insufficient funds);
     * return a failure PaymentResult so that any PaymentMethod can be substituted (LSP).
     */
    public abstract PaymentResult processPayment(double amount);
}
