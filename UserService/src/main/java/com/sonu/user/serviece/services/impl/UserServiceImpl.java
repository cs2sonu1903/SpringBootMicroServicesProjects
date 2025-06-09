package com.sonu.user.serviece.services.impl;

import com.sonu.user.serviece.entities.Hotel;
import com.sonu.user.serviece.entities.Rating;
import com.sonu.user.serviece.entities.User;
import com.sonu.user.serviece.exceptions.ResourceNotFoundException;
import com.sonu.user.serviece.external.service.HotelService;
import com.sonu.user.serviece.repository.UserRepository;
import com.sonu.user.serviece.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        //genrate unique user id
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repositry
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !!" + userId));
        // fetch rating of the abovr user from RATING SERVICE
        //http://localhost:8083/ratings/users/6cbd0b87-7535-43db-85b2-399408562911
//       Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/" + user.getUserId(), Rating[].class);
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        logger.info("{} ",ratingsOfUser);
        List<Rating> ratiingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
            //http://localhost:8082/hotels/64f594ed-37d3-4523-a814-2b612b84fa72
            //static url
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);

            //dynamic url
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
            // Using FeignClient
            Hotel hotel = hotelService.getHotel(rating.getHotelId());


//            logger.info("response status code: {}",forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;

        }).collect(Collectors.toList());
        user.setRatings(ratings );

        return user;
    }

}
