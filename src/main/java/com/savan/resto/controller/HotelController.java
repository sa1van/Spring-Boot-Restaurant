package com.savan.resto.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.savan.resto.dto.Cities;
import com.savan.resto.dto.HotelName;
import com.savan.resto.entity.Hotel;
import com.savan.resto.service.CityService;
import com.savan.resto.service.CuisineService;
import com.savan.resto.service.HotelService;
import org.apache.tomcat.util.json.JSONParser;
import org.aspectj.weaver.bcel.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("hotel/")
public class HotelController {

    @Autowired
    private CityService cityService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private CuisineService cuisineService;

    @PostMapping("/")
    public Boolean saveHotel(@RequestBody Hotel hotel) {
        return hotelService.saveHotel(hotel);
    }

    @GetMapping("/")
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    //http://localhost:8082/hotel/preference?city=AHMEDABAD&cuisine=BENGALI
    //http://localhost:8082/hotel/preference?city=AHMEDABAD&cuisine=NON+VEG
    // above + adds a space, makes NON VEG
    @GetMapping("/preference")
    public List<HotelName> getAllHotelsWithPreferenceCuisineAndCity(@RequestParam(name="cuisine",defaultValue = "ITALIAN") String cuisinePref,
                                                                    @RequestParam(name="city",defaultValue = "PUNE") String cityPref) {

        return hotelService.getAllHotelsWithPreferenceCuisineAndCity(cuisinePref,cityPref);
    }

    //http://localhost:8082/hotel/preferenceListCuisine?cuisines=ITALIAN,PUNJABI&city=PUNE
    @GetMapping("/preferenceListCuisine")
    public List<Hotel> getAllHotelsWithPreferenceCuisinesAndCity(@RequestParam(name="cuisines") List<String> cuisinePrefList,
                                                                  @RequestParam(name="city",defaultValue = "PUNE") String cityPref) {
        return hotelService.getAllHotelsWithPreferenceCuisinesAndCity(cuisinePrefList,cityPref);

    }

//    The encodeURI() function is used to encode a URI.
//    This function encodes special characters, except: , / ? : @ & = + $ #
//            (Use encodeURIComponent() to encode these characters).
//    decodeURIComponent(string) to decode

//    encodeURIComponent will not encode "\" correctly you need to use "\\", reason below


//      Code                meaning
//  \'	equalsTo       '  Single quote
//  \"	equalsTo       "  Double quote
//  \\	equalsTo	    \  Backslash


//    let obj = {
//            cities : ["PUNE","BANGALORE"],
//            mostPreferedCity:"PUNE"
//    }
//    console.log( encodeURIComponent(JSON.stringify(obj))) //json stringify must for objects
//    %7Bcities%3A%5B%22PUNE%22%2C%22BANGALORE%22%5D%2CmostPreferedCity%3A%22PUNE%22%7D

    //{cities:["PUNE","BANGALORE"],mostPreferedCity:"PUNE"}
    //Below is URL encoded format of this object
    //http://localhost:8082/hotel/preferenceCity?obj=%7Bcities%3A%5B%22PUNE%22%2C%22BANGALORE%22%5D%2CmostPreferedCity%3A%22PUNE%22%7D
    @GetMapping("/preferenceCity")
    public void getHotelsForGivenCities(@RequestParam (name="obj") String s) {

        Gson gson = new Gson();
        Cities cObj = gson.fromJson(s, Cities.class);

        System.out.println("Cities array is ");

        for(int i=0;i<cObj.cities.size();i++) {
            System.out.println(cObj.cities.get(i));
        }

        System.out.println("Most Prefered City is " + cObj.mostPreferedCity);

    }

    //http://localhost:8082/hotel/getIdsAsList?list=%5B1%2C2%2C3%5D
    //http://localhost:8082/hotel/preferenceCity?list=[1,2,3]
    @GetMapping("/getIdsAsList")
    public void getIdsAsList(@RequestParam (name="list") String s) {

        Gson gson = new Gson();
        List<Long> sL = gson.fromJson(s, List.class);

        for (int i = 0; i < sL.size(); i++) {
            System.out.println(sL.get(i));
        }
    }


    @DeleteMapping("/{id}")
    public Boolean deleteByHotelId(@PathVariable Long id) {
        return hotelService.deleteByHotelId(id);
    }

    @PutMapping("/{id}")
    public Boolean editHotelById(@PathVariable Long id,@RequestBody Hotel hotel) {
        return hotelService.editHotelById(hotel,id);
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


