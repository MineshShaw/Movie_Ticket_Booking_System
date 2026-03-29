package com.ticketbookingsystem.strategy.pricing;

import com.ticketbookingsystem.model.Show;
import com.ticketbookingsystem.model.Seat;
import java.util.List;

public interface PricingStrategy {
    double calculatePrice(Show show, List<Seat> seats);
}