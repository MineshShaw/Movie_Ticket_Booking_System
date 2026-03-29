package com.ticketbookingsystem.model;

import java.time.LocalDateTime;
import java.util.List;

import com.ticketbookingsystem.model.enums.BookingState;

public class Booking {
    private String bookingId;
    private String userId;
    private Show show;
    private List<Seat> seats;
    private BookingState state;
    private double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime expiryTime;

    public Booking() {}

    public Booking(String bookingId, String userId, Show show, List<Seat> seats, double totalPrice, LocalDateTime createdAt, LocalDateTime expiryTime) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.show = show;
        this.seats = seats;
        this.state = BookingState.CREATED;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.expiryTime = expiryTime;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public BookingState getState() {
        return state;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
