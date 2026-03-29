package com.ticketbookingsystem.strategy.refund;

import com.ticketbookingsystem.model.Booking;

public interface RefundPolicy {
    double calculateRefund(Booking booking);
}
