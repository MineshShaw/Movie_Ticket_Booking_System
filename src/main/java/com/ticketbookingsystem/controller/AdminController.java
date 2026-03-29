package com.ticketbookingsystem.controller;

import com.ticketbookingsystem.model.*;
import com.ticketbookingsystem.service.AdminService;

public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public void addMovie(Movie movie) {
        adminService.addMovie(movie);
    }

    public void addTheatre(Theatre theatre) {
        adminService.addTheatre(theatre);
    }

    public void addShow(Show show) {
        adminService.addShow(show);
    }

    public void addCity(City city) {
        adminService.addCity(city);
    }
}
