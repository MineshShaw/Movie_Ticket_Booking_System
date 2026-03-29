package com.ticketbookingsystem.scheduler;

import com.ticketbookingsystem.model.Booking;
import com.ticketbookingsystem.model.enums.BookingState;
import com.ticketbookingsystem.manager.BookingManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BookingExpiryScheduler {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public ScheduledFuture<?> schedule(LocalDateTime expiryTime, Booking booking, BookingManager bookingManager) {
        long delay = Duration.between(LocalDateTime.now(), expiryTime).toMillis();

        return executorService.schedule(() -> {
            try {
                processExpiry(booking, bookingManager);
            } catch (Exception e) {
                System.err.println("Error during booking expiry: " + e.getMessage());
            }
        }, Math.max(0, delay), TimeUnit.MILLISECONDS);
    }

    private void processExpiry(Booking booking, BookingManager bookingManager) {
        if (booking.getState() == BookingState.CONFIRMED || booking.getState() == BookingState.CANCELLED) {
            return;
        }

        while (booking.getState() == BookingState.PAYMENT_IN_PROGRESS) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (booking.getState() != BookingState.CONFIRMED) {
            System.out.println("Expiry criteria met for booking: " + booking.getBookingId());
            bookingManager.expireBooking(booking.getBookingId());
        }
    }
}