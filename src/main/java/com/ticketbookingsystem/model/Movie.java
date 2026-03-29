package com.ticketbookingsystem.model;

public class Movie {
    private String movieId;
    private String title;
    private String language;
    private int duration;
    private PriceComponent priceComponent;

    public Movie(String movieId, String title, String language, int duration, PriceComponent priceComponent) {
        this.movieId = movieId;
        this.title = title;
        this.language = language;
        this.duration = duration;
        this.priceComponent = priceComponent;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public int getDuration() {
        return duration;
    }

    public double calculatePrice(double basePrice) {
        return priceComponent.apply(basePrice);
    }
}
