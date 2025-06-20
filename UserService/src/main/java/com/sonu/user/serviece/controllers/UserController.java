package com.sonu.user.serviece.controllers;

import com.sonu.user.serviece.entities.User;
import com.sonu.user.serviece.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);

    }
    //single user get
    int retryCount=1;
    @GetMapping("/{userId}")
    @CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//    @Retry(name="ratingHotelService",fallbackMethod = "ratingHotelFallback") //retry method
//    @RateLimiter(name="userRateLimiter",fallbackMethod ="ratingHotelFallback" ) //rate limiter
    public ResponseEntity<User> getSingleUser(@PathVariable String userId)
    {
        logger.info("Retry count: {}"+retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    // creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
        logger.info("Fallback is executed because service is down : ", ex.getMessage());
//        ex.printStackTrace();

        User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This user is created becouse some service is down").userId("45685").build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    // get all user
    @GetMapping
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
