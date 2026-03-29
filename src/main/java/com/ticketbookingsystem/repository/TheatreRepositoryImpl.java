package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Theatre;
import java.util.HashMap;
import java.util.Map;

public class TheatreRepositoryImpl implements TheatreRepository {
    private Map<String, Theatre> theatreMap;

    public TheatreRepositoryImpl() {
        this.theatreMap = new HashMap<>();
    }

    @Override
    public void addTheatre(Theatre theatre) {
        theatreMap.put(theatre.getTheatreId(), theatre);
    }

    @Override
    public Theatre getTheatre(String theatreId) {
        return theatreMap.get(theatreId);
    }
}
