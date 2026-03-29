package com.ticketbookingsystem.model;

import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

import com.ticketbookingsystem.model.enums.SeatState;

public class Screen {
    private String screenId;
    private String name;
    private Theatre theatre;
    private Seat[][] seats;
    private TreeSet<Show> shows;
    private PriceComponent priceComponent;

    public Screen(String screenId, String name, Seat[][] seats, PriceComponent priceComponent) {
        this.screenId = screenId;
        this.name = name;
        this.priceComponent = priceComponent;
        this.shows = new TreeSet<>((s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime()));
        this.seats = seats;
    }

    public String getScreenId() {
        return screenId;
    }

    public String getName() {
        return name;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public TreeSet<Show> getShows() {
        return shows;
    }

    public double calculatePrice(double basePrice) {
        return priceComponent.apply(basePrice);
    }

    public Map<String, SeatState> getSeatMap() {
        Map<String, SeatState> seatMap = new HashMap<>();
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                seatMap.put(seat.getSeatId(), SeatState.AVAILABLE);
            }
        }
        return seatMap;
    }
}
