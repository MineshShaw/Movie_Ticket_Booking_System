package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Movie;

public interface MovieRepository {
    public void addMovie(Movie movie);
    public Movie getMovieById(String movieId);
}
