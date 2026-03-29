package com.ticketbookingsystem.manager;

import com.ticketbookingsystem.model.*;
import com.ticketbookingsystem.model.enums.BookingState;
import com.ticketbookingsystem.observer.PaymentStatusObserver;
import com.ticketbookingsystem.repository.BookingRepository;
import com.ticketbookingsystem.repository.ShowRepository;
import com.ticketbookingsystem.scheduler.BookingExpiryScheduler;
import com.ticketbookingsystem.strategy.pricing.PricingStrategy;
import com.ticketbookingsystem.strategy.refund.RefundPolicy;
import com.ticketbookingsystem.service.PaymentService;
import com.ticketbookingsystem.model.enums.PaymentState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

public class BookingManager implements PaymentStatusObserver {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final SeatLockManager seatLockManager;
    private final PricingStrategy pricingStrategy;
    private final RefundPolicy refundPolicy;
    private final PaymentService paymentService;
    private final BookingExpiryScheduler bookingExpiryScheduler;
    private final Map<String, ScheduledFuture<?>> expiryTasks = new ConcurrentHashMap<>();

    public BookingManager(BookingRepository bookingRepository, ShowRepository showRepository, SeatLockManager seatLockManager,
                          PricingStrategy pricingStrategy, RefundPolicy refundPolicy,
                          PaymentService paymentService, BookingExpiryScheduler bookingExpiryScheduler) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.seatLockManager = seatLockManager;
        this.pricingStrategy = pricingStrategy;
        this.refundPolicy = refundPolicy;
        this.paymentService = paymentService;
        this.bookingExpiryScheduler = bookingExpiryScheduler;
        this.paymentService.subscribe(this);

    }

    public Booking createBooking(String userId, String showId, List<Seat> seats) {
        if (!seatLockManager.lockSeats(showId, seats)) {
            throw new RuntimeException("Seats are already locked or booked.");
        }

        double amount = pricingStrategy.calculatePrice(showRepository.getShow(showId), seats);

        String bookingId = UUID.randomUUID().toString();
        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setState(BookingState.LOCKED);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setExpiryTime(LocalDateTime.now().plusSeconds(30));
        booking.setUserId(userId);
        booking.setShow(showRepository.getShow(showId));
        booking.setSeats(seats);
        booking.setTotalPrice(amount);
        
        bookingRepository.addBooking(booking);

        ScheduledFuture<?> expiryTask = bookingExpiryScheduler.schedule(booking.getExpiryTime(), booking, this);
        expiryTasks.put(booking.getBookingId(), expiryTask);

        initiatePayment(booking);

        return booking;
    }

    public void expireBooking(String bookingId) {
        Booking booking = bookingRepository.getBooking(bookingId);
        if (booking == null || booking.getState() == BookingState.CONFIRMED) return;

        booking.setState(BookingState.EXPIRED);
        System.out.println("Booking " + bookingId + " has expired.");
        expiryTasks.remove(bookingId);
    }

    public void confirmBooking(String bookingId) {
        Booking booking = bookingRepository.getBooking(bookingId);
        if (booking != null) {
            booking.setState(BookingState.CONFIRMED);
        }
        expiryTasks.remove(bookingId);
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.getBooking(bookingId);
        if (booking != null) {
            double refundAmount = refundPolicy.calculateRefund(booking);
            booking.setState(BookingState.CANCELLED);
            seatLockManager.releaseSeats(booking.getShow().getShowId(), booking.getSeats());
            paymentService.refund(booking.getBookingId(), refundAmount);
            expiryTasks.remove(booking.getBookingId());
        }
    }

    public Booking getBookingById(String bookingId) {
        return bookingRepository.getBooking(bookingId);
    }
    
    public Payment initiatePayment(Booking booking) {
        booking.setState(BookingState.PAYMENT_IN_PROGRESS);
        return paymentService.initiatePayment(booking);
    }

    @Override
    public void onPaymentStateUpdate(String bookingId, PaymentState status) {
        if (status == PaymentState.SUCCESS) {
            expiryTasks.get(bookingId).cancel(false);
            confirmBooking(bookingId);
        } else if (status == PaymentState.FAILED) {
            cancelBooking(bookingId);
        }
    }
}