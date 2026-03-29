package com.ticketbookingsystem.controller;

import com.ticketbookingsystem.model.Booking;
import com.ticketbookingsystem.service.BookingService;
import com.ticketbookingsystem.model.Seat;
import java.util.List;

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Booking createBooking(String userId, String showId, List<Seat> seats) {
        try {
            Booking booking = bookingService.bookTicket(userId, showId, seats);
            return booking;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean cancelBooking(String bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}