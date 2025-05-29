package com.sonu.rating.RatingService.services.impl;

import com.sonu.rating.RatingService.entities.Rating;
import com.sonu.rating.RatingService.repository.RatingRepository;
import com.sonu.rating.RatingService.services.RatingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServicesImplemantation implements RatingServices {

    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public Rating create(Rating rating) {
        String ratingId = UUID.randomUUID().toString();
        rating.setRatingId(ratingId);
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getRating() {

        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserID(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
