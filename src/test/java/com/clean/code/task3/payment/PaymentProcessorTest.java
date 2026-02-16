package com.clean.code.task3.payment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorTest {

    private final PaymentProcessor processor = new PaymentProcessor();

    @Test
    void creditCard_valid_processesSuccessfully() {
        PaymentMethod payment = new CreditCardPayment("4111111111111111", "123", true);
        PaymentResult result = processor.makePayment(payment, 50.0);
        assertTrue(result.isSuccess());
    }

    @Test
    void creditCard_invalid_failsValidation() {
        PaymentMethod payment = new CreditCardPayment("invalid", "12", false);
        PaymentResult result = processor.makePayment(payment, 50.0);
        assertFalse(result.isSuccess());
    }

    @Test
    void payPal_linkedToBank_processesSuccessfully() {
        PaymentMethod payment = new PayPalPayment("user@example.com", true);
        PaymentResult result = processor.makePayment(payment, 25.0);
        assertTrue(result.isSuccess());
    }

    @Test
    void payPal_notLinkedToBank_failsValidation_noException() {
        PaymentMethod payment = new PayPalPayment("user@example.com", false);
        PaymentResult result = processor.makePayment(payment, 25.0);
        assertFalse(result.isSuccess());
        // LSP: must not throw UnsupportedOperationException
    }

    @Test
    void anyPaymentMethod_substitutable_throughProcessor() {
        PaymentMethod card = new CreditCardPayment("4111111111111111", "123", true);
        PaymentMethod paypal = new PayPalPayment("u@x.com", true);
        assertTrue(processor.makePayment(card, 10.0).isSuccess());
        assertTrue(processor.makePayment(paypal, 10.0).isSuccess());
    }
}
