package com.ticketbookingsystem.repository;

import java.util.List;

import com.ticketbookingsystem.model.Show;

public interface ShowRepository {
    public void addShow(Show show);
    public Show getShow(String showId);
    public List<Show> getAllShows();
}
