package com.ticketbookingsystem.repository;

import java.util.Map;
import java.util.HashMap;

import com.ticketbookingsystem.model.Movie;

public class MovieRepositoryImpl implements MovieRepository {
    Map<String, Movie> movieMap;

    public MovieRepositoryImpl() {
        this.movieMap = new HashMap<>();
    }

    @Override
    public void addMovie(Movie movie) {
        movieMap.put(movie.getMovieId(), movie);
    }

    @Override
    public Movie getMovieById(String movieId) {
        return movieMap.get(movieId);
    }
}
