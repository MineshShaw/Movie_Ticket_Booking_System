package com.ticketbookingsystem.service;

import com.ticketbookingsystem.model.*;
import com.ticketbookingsystem.repository.*;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    private final CityRepository cityRepository;
    private final TheatreRepository theatreRepository;

    public SearchService(CityRepository cityRepo, TheatreRepository theatreRepo) {
        this.cityRepository = cityRepo;
        this.theatreRepository = theatreRepo;
    }

    public List<City> getAllCities() {
        return cityRepository.getAllCities();
    }

    public List<Movie> getMoviesInCity(String cityName) {
        return getShowsByCity(cityName).stream()
                .map(Show::getMovie)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Show> getShowsByMovieInCity(String cityName, String movieId) {
        return getShowsByCity(cityName).stream()
                .filter(show -> show.getMovie().getMovieId().equals(movieId))
                .collect(Collectors.toList()); 
    }

    public List<Theatre> getTheatresInCity(String cityName) {
        return cityRepository.getCity(cityName).getTheatres();
    }

    public List<Show> getShowsInTheatre(String theatreId) {
        return theatreRepository.getTheatre(theatreId).getScreens().stream()
                .flatMap(screen -> screen.getShows().stream())
                .collect(Collectors.toList());
    }

    private List<Show> getShowsByCity(String cityName) {
        return getTheatresInCity(cityName).stream()
                .flatMap(theatre -> theatre.getScreens().stream())
                .flatMap(screen -> screen.getShows().stream())
                .collect(Collectors.toList());
    }
}