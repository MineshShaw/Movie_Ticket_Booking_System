package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Payment;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentRepositoryImpl implements PaymentRepository {
    private final Map<String, Payment> payments = new ConcurrentHashMap<>();

    @Override
    public void save(Payment payment) {
        payments.put(payment.getBookingId(), payment);
    }

    @Override
    public Payment getByBookingId(String bookingId) {
        return payments.get(bookingId);
    }
}
