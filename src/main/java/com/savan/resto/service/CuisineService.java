package com.savan.resto.service;

import com.savan.resto.dto.HotelAndCuisine;
import com.savan.resto.dto.HotelAndCuisineList;
import com.savan.resto.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CuisineService {

    @Autowired
    private CuisineRepository cuisineRepository;

    public List<HotelAndCuisineList> getAllCuisinesWithHotel() {
        Map<String,List<String> > mp = new HashMap<>();
        List<HotelAndCuisine> hcList = cuisineRepository.getAllCuisines();
        for(int i=0;i<hcList.size();i++) {

            String hotelName = hcList.get(i).getHotelName();
            String cuisine = hcList.get(i).getCuisine();

            if(mp.get(hotelName)==null) {
                mp.put(hotelName, new ArrayList<>());
                mp.get(hotelName).add(cuisine);
            }
            else
                mp.get(hotelName).add(cuisine);
        }
        List<HotelAndCuisineList> hacl = new ArrayList<>();
        for (Map.Entry<String,List<String>> ite : mp.entrySet()) {
            HotelAndCuisineList obj = new HotelAndCuisineList();
            obj.hotelName = ite.getKey();
            obj.cuisines = ite.getValue();
            hacl.add(obj);
        }
        return hacl;
    }
}
