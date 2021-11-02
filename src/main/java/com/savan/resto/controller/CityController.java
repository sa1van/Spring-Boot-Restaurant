package com.savan.resto.controller;


import com.savan.resto.entity.City;
import com.savan.resto.entity.Hotel;
import com.savan.resto.service.CityService;
import com.savan.resto.service.CuisineService;
import com.savan.resto.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/")
    public Set<City> getAllCities() {
        return cityService.getAllCities();
    }

    @RequestMapping(value="**",method = {
            RequestMethod.GET,
            RequestMethod.DELETE,
            RequestMethod.POST,
            RequestMethod.PUT
    })
    public Boolean getAnythingelse() {
        return false;
    }

}
