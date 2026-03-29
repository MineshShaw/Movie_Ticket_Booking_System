package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.City;

import java.util.List;

public interface CityRepository {
    public void addCity(City city);
    public City getCity(String cityName);
    public List<City> getAllCities();
}
