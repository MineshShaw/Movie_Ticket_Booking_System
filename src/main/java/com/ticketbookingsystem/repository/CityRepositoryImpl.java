package com.ticketbookingsystem.repository;

import com.ticketbookingsystem.model.City;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CityRepositoryImpl implements CityRepository {
    private Map<String, City> cityMap;

    public CityRepositoryImpl() {
        this.cityMap = new HashMap<>();
    }

    @Override
    public void addCity(City city) {
        cityMap.put(city.getName(), city);
    }


    @Override
    public City getCity(String cityName) {
        return cityMap.get(cityName);
    }

    @Override
    public List<City> getAllCities() {
        return List.copyOf(cityMap.values());
    }
}
