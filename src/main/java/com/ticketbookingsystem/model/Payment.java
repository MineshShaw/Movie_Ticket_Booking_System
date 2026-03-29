package com.ticketbookingsystem.model;

import java.time.LocalDateTime;

import com.ticketbookingsystem.model.enums.PaymentState;

public class Payment {
    private String paymentId;
    private String bookingId;
    private double amount;
    private LocalDateTime timestamp;
    private PaymentState state;

    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setState(String state) {
        this.state = PaymentState.valueOf(state);
    }
}
