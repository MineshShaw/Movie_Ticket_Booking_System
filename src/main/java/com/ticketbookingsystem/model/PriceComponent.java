package com.ticketbookingsystem.model;

public class PriceComponent {
    private final double flatFee;
    private final double multiplier;

    public PriceComponent(double flatFee, double multiplier) {
        this.flatFee = flatFee;
        this.multiplier = multiplier;
    }

    public double apply(double base) {
        return (base * multiplier) + flatFee;
    }
}