package com.ticketbookingsystem.client;

import com.ticketbookingsystem.controller.*;
import com.ticketbookingsystem.model.*;
import com.ticketbookingsystem.model.enums.UserRole;

import java.util.List;

public class MovieTicketBookingClient {
    private final AdminController adminController;
    private final SearchController searchController;
    private final BookingController bookingController;

    public MovieTicketBookingClient(AdminController adminController, 
                                    SearchController searchController, 
                                    BookingController bookingController) {
        this.adminController = adminController;
        this.searchController = searchController;
        this.bookingController = bookingController;
    }

    public Booking bookTicket(User user, String showId, List<Seat> seats) {
        return bookingController.createBooking(user.getUserId(), showId, seats);
    }

    public void cancelBooking(String bookingId) {
        if (!bookingController.cancelBooking(bookingId)) {
            throw new RuntimeException("Failed to cancel booking.");
        }
    }

    public List<Theatre> showTheatre(String cityName) {
        return searchController.showTheatres(cityName);
    }

    public List<Movie> showMovies(String cityName) {
        return searchController.showMovies(cityName);
    }

    public List<Show> showShowsInTheatre(String theatreId) {
        return searchController.showShowsInTheatre(theatreId);
    }

    public List<Show> showTheatresByMovie(String cityName, String movieId) {
        return searchController.showTheatresByMovie(cityName, movieId);
    }

    public void addTheatre(User user, Theatre theatre) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized: Only admins can add theatres.");
        }
        adminController.addTheatre(theatre);
    }

    public void addCity(User user, City city) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized: Only admins can add cities.");
        }
        adminController.addCity(city); 
    }

    public void addMovie(User user, Movie movie) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized: Only admins can add movies.");
        }
        adminController.addMovie(movie);
    }

    public void addShow(User user, Show show) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized: Only admins can add shows.");
        }
        adminController.addShow(show);
    }
}