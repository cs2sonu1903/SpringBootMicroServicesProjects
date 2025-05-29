package com.sonu.rating.RatingService.services;

import com.sonu.rating.RatingService.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingServices {
    //create
    Rating create(Rating rating);

    //get all rating
    List<Rating> getRating();

    //get all by UserId
    List<Rating> getRatingByUserID(String userId);

    //get all by hotelId
    List<Rating> getRatingByHotelId(String hotelId);

}
