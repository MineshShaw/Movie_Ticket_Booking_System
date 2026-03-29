package com.ticketbookingsystem.model;

import java.util.List;

public class Theatre {
    private String theatreId;
    private City city;
    private String name;
    private List<Screen> screens;
    private PriceComponent priceComponent;

    public Theatre(String theatreId, City city, String name, List<Screen> screens, PriceComponent priceComponent) {
        this.theatreId = theatreId;
        this.city = city;
        this.name = name;
        this.priceComponent = priceComponent;
        this.screens = screens;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public City getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public double calculatePrice(double basePrice) {
        return priceComponent.apply(basePrice);
    }
}
