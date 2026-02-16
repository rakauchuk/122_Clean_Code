package com.clean.code.task3.payment;

/**
 * Credit card payment. Validates card details; processes via card network.
 */
public class CreditCardPayment extends PaymentMethod {

    private final String cardNumber;
    private final String cvv;
    private final boolean validCard;

    public CreditCardPayment(String cardNumber, String cvv, boolean validCard) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.validCard = validCard;
    }

    @Override
    public boolean validatePaymentDetails() {
        return validCard && cardNumber != null && !cardNumber.isBlank() && cvv != null && cvv.length() >= 3;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (amount <= 0) {
            return PaymentResult.failure("Amount must be positive");
        }
        // Simulate processing
        return PaymentResult.ok();
    }
}
