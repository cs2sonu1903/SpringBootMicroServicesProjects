package com.sonu.rating.RatingService.repository;

import com.sonu.rating.RatingService.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingRepository extends JpaRepository<Rating,String> {

    //custom method
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
