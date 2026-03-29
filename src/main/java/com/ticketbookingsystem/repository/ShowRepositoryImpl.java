package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.Show;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ShowRepositoryImpl implements ShowRepository{
    private Map<String, Show> showMap;

    public ShowRepositoryImpl() {
        this.showMap = new HashMap<>();
    }

    @Override
    public void addShow(Show show) {
        showMap.put(show.getShowId(), show);
    }

    @Override
    public Show getShow(String showId) {
        return showMap.get(showId);
    }

    @Override
    public List<Show> getAllShows() {
        return new ArrayList<>(showMap.values());
    }
}
