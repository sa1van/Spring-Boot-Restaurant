package com.savan.resto.service;

import com.savan.resto.entity.City;
import com.savan.resto.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public Set<City> getAllCities() {
        Set<City> cities = new HashSet<>(cityRepository.findAll());
        return cities;
    }
}
