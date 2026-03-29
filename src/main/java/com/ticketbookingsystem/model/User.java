package com.ticketbookingsystem.model;

import com.ticketbookingsystem.model.enums.UserRole;

public class User {
    private String userId;
    private String name;
    private String email;
    private UserRole role;

    public User(String userId, String name, String email, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
}