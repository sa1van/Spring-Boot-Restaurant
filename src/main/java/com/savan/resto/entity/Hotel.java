package com.savan.resto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.savan.resto.dto.CityAndCuisine;

import javax.persistence.*;
import java.util.List;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String hotelName;

    @Column
    private String hotelDescription;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(
            name = "city_id"
    )
    private City city;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH
            },
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "hotel_cuisine",
            joinColumns = { @JoinColumn(name = "hotel_id") },
            inverseJoinColumns = { @JoinColumn(name = "cuisine_id") }
    )
    private List<Cuisine> cuisines;

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public void setHotelDescription(String hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

//    @JsonBackReference
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
