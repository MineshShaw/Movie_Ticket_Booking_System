package com.ticketbookingsystem.strategy.refund;

import com.ticketbookingsystem.model.Booking;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeBasedRefundPolicy implements RefundPolicy {
    @Override
    public double calculateRefund(Booking booking) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime showTime = booking.getShow().getStartTime();
        long hoursUntilShow = Duration.between(now, showTime).toHours();

        if (hoursUntilShow >= 24) {
            return booking.getTotalPrice();
        } else if (hoursUntilShow >= 6) {
            return booking.getTotalPrice() * 0.5;
        } else {
            return 0.0; 
        }
    }
}
