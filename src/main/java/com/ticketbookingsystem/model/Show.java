package com.ticketbookingsystem.model;

import java.time.LocalDateTime;
import java.util.Map;

import com.ticketbookingsystem.model.enums.SeatState;

public class Show implements Comparable<Show> {
    private String showId;
    private Movie movie;
    private Screen screen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Map<String, SeatState> seatStates;

    public Show(String showId, Movie movie, Screen screen, LocalDateTime startTime, LocalDateTime endTime, Map<String, SeatState> seatStates) {
        this.showId = showId;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seatStates = seatStates;
    }

    public String getShowId() {
        return showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Map<String, SeatState> getSeatStates() {
        return seatStates;
    }

    @Override
    public int compareTo(Show other) {
        if (this.startTime.equals(other.startTime)) {
            return this.showId.compareTo(other.showId);
        }
        return this.startTime.compareTo(other.startTime);
    }
}
