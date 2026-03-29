package com.ticketbookingsystem.service;

import com.ticketbookingsystem.model.Payment;
import com.ticketbookingsystem.model.enums.PaymentState;
import com.ticketbookingsystem.repository.PaymentRepository;
import com.ticketbookingsystem.model.Refund;
import com.ticketbookingsystem.observer.PaymentStatusObserver;
import com.ticketbookingsystem.proxy.PaymentProxy;
import com.ticketbookingsystem.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final List<PaymentStatusObserver> observers;
    private final PaymentProxy paymentProxy;

    public PaymentService(PaymentRepository paymentRepository, PaymentProxy paymentProxy) {
        this.paymentRepository = paymentRepository;
        this.observers = new ArrayList<>();
        this.paymentProxy = paymentProxy;
    }

    public Payment initiatePayment(Booking booking) {
        Payment payment = new Payment();
        payment.setBookingId(booking.getBookingId());
        payment.setState(PaymentState.INITIATED);
        paymentRepository.save(payment);
        paymentProxy.pay(booking.getBookingId(), booking.getTotalPrice(), this);
        return payment;
    }

    public void subscribe(PaymentStatusObserver observer) {
        observers.add(observer);
    }

    public void handlePaymentSuccess(String bookingId) {
        Payment payment = paymentRepository.getByBookingId(bookingId);
        if (payment != null) {
            payment.setState(PaymentState.SUCCESS);
            paymentRepository.save(payment);
            notifyObservers(bookingId, PaymentState.SUCCESS);
        } else {
            System.out.println("Payment not found for booking: " + bookingId);
        }
    }

    public void handlePaymentFailure(String bookingId) {
        Payment payment = paymentRepository.getByBookingId(bookingId);
        if (payment != null) {
            payment.setState(PaymentState.FAILED);
            paymentRepository.save(payment);
            notifyObservers(bookingId, PaymentState.FAILED);
        } else {
            System.out.println("Payment not found for booking: " + bookingId);
        }
    }

    public void refund(String bookingId, double amount) {
        Payment payment = paymentRepository.getByBookingId(bookingId);
        Refund refund = new Refund(payment);
        
        if (payment != null && payment.getState() == PaymentState.SUCCESS) {
            System.out.println("Refund of amount " + amount + " initiated for booking: " + bookingId);
            paymentRepository.save(refund);
        } else {
            System.out.println("No successful payment found for booking: " + bookingId);
        }
    }

    private void notifyObservers(String bookingId, PaymentState status) {
        for (PaymentStatusObserver observer : observers) {
            observer.onPaymentStateUpdate(bookingId, status);
        }
    }
}
