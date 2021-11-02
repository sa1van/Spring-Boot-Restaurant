package com.savan.resto.repository;

import com.savan.resto.dto.HotelAndCuisine;
import com.savan.resto.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CuisineRepository extends JpaRepository<Cuisine,Long> {

    @Query(
            value = "select c.cuisine as cuisine,h.hotel_name as hotelName from cuisine c INNER JOIN hotel_cuisine hc ON c.id = hc.cuisine_id " +
            "INNER JOIN hotel h ON h.id = hc.hotel_id",
            nativeQuery = true
    )
    public List<HotelAndCuisine> getAllCuisines();

    public Cuisine findByCuisine(String cuisine);

}
