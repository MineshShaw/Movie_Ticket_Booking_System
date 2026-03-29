package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Theatre;

public interface TheatreRepository {
    public void addTheatre(Theatre theatre);
    public Theatre getTheatre(String theatreId);
}
