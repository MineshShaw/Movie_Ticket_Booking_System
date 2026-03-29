package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Booking;
import java.util.Map;
import java.util.HashMap;

public class BookingRepositoryImpl implements BookingRepository {
    private Map<String, Booking> bookingMap;

    public BookingRepositoryImpl() {
        this.bookingMap = new HashMap<>();
    }

    @Override
    public void addBooking(Booking booking) {
        bookingMap.put(booking.getBookingId(), booking);
    }

    @Override
    public Booking getBooking(String bookingId) {
        return bookingMap.get(bookingId);
    }
    
}
