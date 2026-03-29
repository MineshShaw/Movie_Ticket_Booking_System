package com.ticketbookingsystem.manager;

import java.util.List;
import java.util.Map;

import com.ticketbookingsystem.repository.ShowRepository;
import com.ticketbookingsystem.model.enums.SeatState;
import com.ticketbookingsystem.model.Seat;

public class SeatLockManager {
    private final ShowRepository showRepository;

    public SeatLockManager(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public synchronized boolean lockSeats(String showId, List<Seat> seats) {
        Map<String, SeatState> showLocks = showRepository.getShow(showId).getSeatStates();

        for (Seat seat : seats) {
            if (showLocks.getOrDefault(seat.getSeatId(), SeatState.AVAILABLE) == SeatState.LOCKED) {
                return false;
            }
        }

        for (Seat seat : seats) {
            showLocks.put(seat.getSeatId(), SeatState.LOCKED);
        }
        return true;
    }

    public void releaseSeats(String showId, List<Seat> seats) {
        Map<String, SeatState> showLocks = showRepository.getShow(showId).getSeatStates();
        for (Seat seat : seats) {
            showLocks.put(seat.getSeatId(), SeatState.AVAILABLE);
        }
    }

    public void confirmSeats(String showId, List<Seat> seatIds) {
        Map<String, SeatState> showLocks = showRepository.getShow(showId).getSeatStates();
        for (Seat seat : seatIds) {
            showLocks.put(seat.getSeatId(), SeatState.BOOKED);
        }
    }
}
