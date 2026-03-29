package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Payment;

public interface PaymentRepository {
    public void save(Payment payment);
    public Payment getByBookingId(String bookingId);
}
