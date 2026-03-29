package com.ticketbookingsystem.strategy.refund;

import com.ticketbookingsystem.model.Booking;

public class FullRefundPolicy implements RefundPolicy {
    @Override
    public double calculateRefund(Booking booking) {
        return booking.getTotalPrice();
    }
}
