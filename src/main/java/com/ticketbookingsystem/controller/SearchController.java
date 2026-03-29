package com.ticketbookingsystem.controller;

import com.ticketbookingsystem.model.*;
import com.ticketbookingsystem.service.SearchService;
import java.util.List;

public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    public List<City> getCities() {
        return searchService.getAllCities();
    }

    public List<Movie> showMovies(String cityName) {
        return searchService.getMoviesInCity(cityName);
    }

    public List<Show> showTheatresByMovie(String cityName, String movieId) {
        return searchService.getShowsByMovieInCity(cityName, movieId);
    }

    public List<Theatre> showTheatres(String cityName) {
        return searchService.getTheatresInCity(cityName);
    }

    public List<Show> showShowsInTheatre(String theatreId) {
        return searchService.getShowsInTheatre(theatreId);
    }
}