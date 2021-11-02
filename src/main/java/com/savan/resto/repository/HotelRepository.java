package com.savan.resto.repository;

import com.savan.resto.dto.HotelName;
import com.savan.resto.entity.City;
import com.savan.resto.entity.Hotel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    @Query(
            value = "select h.hotel_name as hotelName from cuisine c INNER JOIN hotel_cuisine hc ON c.id = hc.cuisine_id " +
                    "INNER JOIN hotel h ON h.id = hc.hotel_id INNER JOIN city ci ON ci.id = h.city_id WHERE ci.city_name = :cityPref and c.cuisine = :cuisinePref",
            nativeQuery = true
    )
    public List<HotelName> getAllHotelsWithPreferenceCuisineAndCity(@Param("cuisinePref") String cuisinePref, @Param("cityPref") String cityPref);

    public List<Hotel> findByCity(City city);
}
