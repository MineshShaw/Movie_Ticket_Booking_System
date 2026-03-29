package com.ticketbookingsystem.proxy;

import com.ticketbookingsystem.service.PaymentService;

public class PaymentProxy {
    private final PaymentGateway paymentGateway;

    public PaymentProxy(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void pay(String bookingId, double amount ,PaymentService paymentService) {
        paymentGateway.processPayment(bookingId, amount, paymentService);
    }
}
