package com.ticketbookingsystem.model;

import com.ticketbookingsystem.model.enums.SeatType;

public class Seat {
    private String seatId;
    private int row;
    private int column;
    private SeatType type;
    private PriceComponent priceComponent;

    public Seat(String seatId, int row, int column, SeatType type, PriceComponent priceComponent) {
        this.seatId = seatId;
        this.row = row;
        this.column = column;
        this.type = type;
        this.priceComponent = priceComponent;
    }

    public String getSeatId() {
        return seatId;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public SeatType getType() {
        return type;
    }

    public double getPrice() {
        return priceComponent.apply(0);
    }
}
