package com.savan.resto.controller;


import com.savan.resto.dto.HotelAndCuisine;
import com.savan.resto.dto.HotelAndCuisineList;
import com.savan.resto.service.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("cuisine")
public class CuisineController {

    @Autowired
    private CuisineService cuisineService;

    @GetMapping("/")
    public List<HotelAndCuisineList> getAllCuisinesWithHotel() {
        return cuisineService.getAllCuisinesWithHotel();
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
