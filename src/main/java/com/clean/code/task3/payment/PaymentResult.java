package com.clean.code.task3.payment;

import java.util.Optional;

/**
 * Result of a payment attempt (avoids throwing from processPayment — LSP-friendly).
 */
public final class PaymentResult {

    private final boolean success;
    private final String message;

    private PaymentResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static PaymentResult ok() {
        return new PaymentResult(true, null);
    }

    public static PaymentResult failure(String message) {
        return new PaymentResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }
}
