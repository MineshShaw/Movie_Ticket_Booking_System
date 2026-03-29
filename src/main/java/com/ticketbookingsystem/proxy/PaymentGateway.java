package com.ticketbookingsystem.proxy;

import com.ticketbookingsystem.service.PaymentService;

public class PaymentGateway {
    public void processPayment(String bookingId, double amount, PaymentService paymentService) {
        System.out.println("Processing payment for Booking ID: " + bookingId + " Amount: $" + amount);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        paymentService.handlePaymentSuccess(bookingId);
    }
}
