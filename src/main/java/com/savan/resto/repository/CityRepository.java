package com.savan.resto.repository;

import com.savan.resto.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

    public City findByCityName(String name);

}
