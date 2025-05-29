package com.sonu.user.serviece.services;

import com.sonu.user.serviece.entities.User;

import java.util.List;

public interface UserService {
    //create user
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user of given userId
    User getUser(String userId);
}
