package com.ticketbookingsystem.strategy.pricing;

import com.ticketbookingsystem.model.*;
import java.util.List;

public class DynamicPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(Show show, List<Seat> seats) {
        double total = 0;

        for (Seat seat : seats) total += seat.getPrice();
        total = show.getScreen().calculatePrice(total);
        total = show.getScreen().getTheatre().calculatePrice(total);
        total = show.getMovie().calculatePrice(total);

        return total;
    }
}
