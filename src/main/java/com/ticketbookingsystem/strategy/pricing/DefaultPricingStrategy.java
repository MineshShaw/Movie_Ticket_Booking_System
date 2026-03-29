package com.ticketbookingsystem.strategy.pricing;

import com.ticketbookingsystem.model.Show;
import com.ticketbookingsystem.model.Seat;
import java.util.List;

public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Show show, List<Seat> seats) {
        return seats.stream()
                .mapToDouble(Seat::getPrice)
                .sum();
    }
}
