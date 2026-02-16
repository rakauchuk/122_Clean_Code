package com.clean.code.task3.payment;

/**
 * Processes payments using any PaymentMethod (LSP: subtypes are substitutable).
 */
public class PaymentProcessor {

    /**
     * Validates and processes the payment. Works uniformly for all payment methods.
     */
    public PaymentResult makePayment(PaymentMethod payment, double amount) {
        if (!payment.validatePaymentDetails()) {
            return PaymentResult.failure("Invalid payment details");
        }
        return payment.processPayment(amount);
    }
}
