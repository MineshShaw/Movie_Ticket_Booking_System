package com.ticketbookingsystem.service;

import com.ticketbookingsystem.manager.BookingManager;
import com.ticketbookingsystem.model.Booking;
import com.ticketbookingsystem.model.Seat;
import java.util.List;

public class BookingService {
    private final BookingManager bookingManager;

    public BookingService(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    public Booking bookTicket(String userId, String showId, List<Seat> seats) {
        return bookingManager.createBooking(userId, showId, seats);
    }

    public void cancelBooking(String bookingId) {
        bookingManager.cancelBooking(bookingId);
    }

    public Booking getBookingById(String bookingId) {
        return bookingManager.getBookingById(bookingId);
    }
}
