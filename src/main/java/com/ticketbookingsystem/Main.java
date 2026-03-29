package com.ticketbookingsystem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import com.ticketbookingsystem.factory.SystemFactory;
import com.ticketbookingsystem.client.MovieTicketBookingClient;
import com.ticketbookingsystem.model.*;
import com.ticketbookingsystem.model.enums.SeatType;
import com.ticketbookingsystem.model.enums.UserRole;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        MovieTicketBookingClient client = SystemFactory.createClient("dynamic", "time_based");

        User adminUser = new User("Admin1", "System Admin", "admin@cinema.com", UserRole.ADMIN);

        System.out.println("--- ADMIN SETUP START ---");

        City bangalore = new City("C1", "Bangalore", new ArrayList<>());
        client.addCity(adminUser, bangalore);

        Seat[][] seatsForScreen1 = generateSeats(5, 5);
        Screen screen1 = new Screen("SCR1", "Audi 1", seatsForScreen1, new PriceComponent(20, 1.0));

        List<Screen> screens = new ArrayList<>();
        screens.add(screen1);
        Theatre theatre1 = new Theatre("T1", bangalore, "PVR Imax", screens, new PriceComponent(100, 1.0));
        screen1.setTheatre(theatre1);
        client.addTheatre(adminUser, theatre1);

        Movie movie1 = new Movie("M1", "Inception", "English", 148, new PriceComponent(100.0, 1.1));
        client.addMovie(adminUser, movie1);

        Show show1 = new Show("S1", movie1, screen1, 
                               LocalDateTime.now().plusHours(2), 
                               LocalDateTime.now().plusHours(5),
                               screen1.getSeatMap());
        client.addShow(adminUser, show1);

        System.out.println("--- ADMIN SETUP COMPLETE ---\n");
        
        int numberOfUsers = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch latch = new CountDownLatch(1);

        List<Seat> contentiousSeats = Arrays.asList(seatsForScreen1[0][0], seatsForScreen1[0][1]);

        List<Seat> user4Seats = Arrays.asList(seatsForScreen1[4][1], seatsForScreen1[4][2], seatsForScreen1[4][3]);
        List<Seat> user5Seats = Arrays.asList(seatsForScreen1[4][4]);

        System.out.println("--- STARTING CONCURRENT BOOKING SIMULATION ---");

        for (int i = 1; i <= numberOfUsers; i++) {
            final String userId = "U" + i;
            final List<Seat> selectedSeats = (i <= 3) ? contentiousSeats : (i == 4 ? user4Seats : user5Seats);
            User user = new User(userId, "Customer " + i, userId + "@example.com", UserRole.USER);

            executor.submit(() -> {
                try {
                    latch.await();
                    System.out.println(userId + " attempting to book seats: " + getSeatIds(selectedSeats));
                    
                    Booking booking = client.bookTicket(user, show1.getShowId(), selectedSeats);
                    
                    if (booking != null) {
                        System.out.println("[SUCCESS] " + userId + " secured booking: " + booking.getBookingId());
                    } else {
                        System.out.println("[FAILED] " + userId + " - Seats already locked or unavailable.");
                    }
                } catch (Exception e) {
                    System.err.println("[ERROR] " + userId + " encountered an issue: " + e.getMessage());
                }
            });
        }

        latch.countDown();
        executor.shutdown();

        executor.shutdown();
        executor.awaitTermination(12, TimeUnit.SECONDS);
        System.out.println("\n--- SIMULATION FINISHED ---");
    }

    private static Seat[][] generateSeats(int rows, int cols) {
        Seat[][] seatGrid = new Seat[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String id = r + "-" + c;
                Seat seat = new Seat(id, r, c, SeatType.REGULAR, new PriceComponent(100.0, 1.0));
                seatGrid[r][c] = seat;
            }
        }
        return seatGrid;
    }

    private static String getSeatIds(List<Seat> seats) {
        return seats.stream().map(Seat::getSeatId).reduce((a, b) -> a + ", " + b).orElse("");
    }
}