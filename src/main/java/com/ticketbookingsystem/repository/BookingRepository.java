package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Booking;

public interface BookingRepository {
    public void addBooking(Booking booking);
    public Booking getBooking(String bookingId);
}
