package com.savan.resto.service;

import com.savan.resto.dto.HotelName;
import com.savan.resto.entity.City;
import com.savan.resto.entity.Cuisine;
import com.savan.resto.entity.Hotel;
import com.savan.resto.repository.CityRepository;
import com.savan.resto.repository.CuisineRepository;
import com.savan.resto.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    public Boolean saveHotel(Hotel hotel) {
        City cityFromDb = cityRepository.findByCityName(hotel.getCity().getCityName());
        if(cityFromDb != null) {
            hotel.setCity(cityFromDb);
        }
        List<Cuisine> cuisineList = new ArrayList<Cuisine>();
        for(int i=0;i< hotel.getCuisines().size();i++) {
            Cuisine cuisineFromDb =  cuisineRepository.findByCuisine(hotel.getCuisines().get(i).getCuisine());
            if(cuisineFromDb!=null) {
                cuisineList.add(cuisineFromDb);
            }
            else {
                cuisineList.add(hotel.getCuisines().get(i));
            }
        }
        hotel.setCuisines(cuisineList);
        hotelRepository.save(hotel);
        return true;
    }

    public List<Hotel> getAllHotels() {
//        System.out.println(hotelRepository.findAll(PageRequest.of(0,2)).getTotalElements());
//        System.out.println(hotelRepository.findAll(PageRequest.of(0,2)).getTotalPages());

//        return hotelRepository.findAll(PageRequest.of(0,2)).toList();
        return hotelRepository.findAll();
    }

    public Boolean deleteByHotelId(Long id) {
        if(hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    public List<HotelName> getAllHotelsWithPreferenceCuisineAndCity(String cuisinePref, String cityPref) {
        return hotelRepository.getAllHotelsWithPreferenceCuisineAndCity(cuisinePref,cityPref);
    }

    public List<Hotel> getAllHotelsWithPreferenceCuisinesAndCity(List<String> cuisinePrefList,String cityName) {
        City city = cityRepository.findByCityName(cityName);
        List<Hotel> hotelList = hotelRepository.findByCity(city);
        List<Hotel> toReturnHotelList = new ArrayList<>();


        for(int i=0;i<hotelList.size();i++) {

            int count = 0;
            for(int j=0;j<hotelList.get(i).getCuisines().size();j++) {

                String c = hotelList.get(i).getCuisines().get(j).getCuisine();

                if(cuisinePrefList.contains(c)){
                    count++;
                }
            }

            if(count == cuisinePrefList.size()) {
                toReturnHotelList.add(hotelList.get(i));
            }
        }

        return toReturnHotelList;
    }

    public Boolean editHotelById(Hotel hotel,Long id) {
        if(hotel.getId() == null) {
            return false;
        }
        City cityFromDb = cityRepository.findByCityName(hotel.getCity().getCityName());
        if(cityFromDb != null) {
            hotel.setCity(cityFromDb);
        }
        List<Cuisine> cuisineList = new ArrayList<Cuisine>();
        for(int i=0;i< hotel.getCuisines().size();i++) {
            Cuisine cuisineFromDb =  cuisineRepository.findByCuisine(hotel.getCuisines().get(i).getCuisine());

            if(cuisineFromDb!=null) {
                cuisineList.add(cuisineFromDb);
            }
            else {
                cuisineList.add(hotel.getCuisines().get(i));
            }
        }
        hotel.setCuisines(cuisineList);
        hotelRepository.save(hotel);
        return true;
    }
}
