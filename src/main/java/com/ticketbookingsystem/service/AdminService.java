package com.ticketbookingsystem.service;

import java.time.LocalDateTime;
import java.util.TreeSet;

import com.ticketbookingsystem.model.*;
import com.ticketbookingsystem.repository.*;

public class AdminService {
    private final CityRepository cityRepository;
    private final TheatreRepository theatreRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;

    public AdminService(CityRepository cr, TheatreRepository tr, MovieRepository mr, ShowRepository sr) {
        this.cityRepository = cr;
        this.theatreRepository = tr;
        this.movieRepository = mr;
        this.showRepository = sr;
    }

    public void addCity(City city) { cityRepository.addCity(city); }

    public void addMovie(Movie movie) { movieRepository.addMovie(movie); }

    public void addTheatre(Theatre theatre) { 
        theatreRepository.addTheatre(theatre);
        cityRepository.getCity(theatre.getCity().getName()).getTheatres().add(theatre);
    }

    public void addScreen(String theatreId, Screen screen) {
        Theatre theatre = theatreRepository.getTheatre(theatreId);
        if (theatre != null) {
            theatre.getScreens().add(screen);
        }
    }

    public boolean addShow(Show newShow) {
        Screen screen = newShow.getScreen();
        TreeSet<Show> existingShows = screen.getShows();

        LocalDateTime newStart = newShow.getStartTime();
        LocalDateTime newEnd = newShow.getEndTime();

        for (Show existingShow : existingShows) {
            LocalDateTime existingStart = existingShow.getStartTime();
            LocalDateTime existingEnd = existingShow.getEndTime();
            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                System.out.println("Schedule Conflict: Screen " + screen.getScreenId() + 
                                   " is already occupied between " + existingStart + " and " + existingEnd);
                return false; 
            }
        }

        showRepository.addShow(newShow);
        screen.getShows().add(newShow); 
        
        return true;
    }
}
