package com.sonu.hotel.services;

import com.sonu.hotel.entites.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel  create(Hotel hotel);

    //get all
    List<Hotel> getAll();

    //get Single

    Hotel get(String id);
}
