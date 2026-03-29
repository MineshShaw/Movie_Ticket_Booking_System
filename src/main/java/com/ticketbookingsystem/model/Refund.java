package com.ticketbookingsystem.model;

import java.time.LocalDateTime;

import com.ticketbookingsystem.model.enums.PaymentState;

public class Refund extends Payment {
    private String refundId;
    private LocalDateTime refundTimestamp;
    private double refundAmount;

    public Refund(Payment payment) {
        this.setBookingId(payment.getBookingId());
        this.setRefundAmount(payment.getAmount());
        this.setRefundTimestamp(LocalDateTime.now());
        this.setRefundId("REFUND-" + payment.getPaymentId());
        super.setState(PaymentState.REFUNDED);
        super.setAmount(payment.getAmount());
        super.setTimestamp(payment.getTimestamp());
        super.setPaymentId(payment.getPaymentId());
        super.setPaymentId(payment.getPaymentId());
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public LocalDateTime getRefundTimestamp() {
        return refundTimestamp;
    }

    public void setRefundTimestamp(LocalDateTime refundTimestamp) {
        this.refundTimestamp = refundTimestamp;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }
}
