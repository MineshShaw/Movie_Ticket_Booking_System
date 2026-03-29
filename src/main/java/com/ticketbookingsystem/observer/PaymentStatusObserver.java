package com.ticketbookingsystem.observer;

import com.ticketbookingsystem.model.enums.PaymentState;

public interface PaymentStatusObserver {
    void onPaymentStateUpdate(String bookingId, PaymentState status);
}
