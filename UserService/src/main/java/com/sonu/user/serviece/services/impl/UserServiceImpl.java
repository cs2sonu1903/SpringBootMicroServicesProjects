package com.sonu.user.serviece.services.impl;

import com.sonu.user.serviece.entities.User;
import com.sonu.user.serviece.exceptions.ResourceNotFoundException;
import com.sonu.user.serviece.repository.UserRepository;
import com.sonu.user.serviece.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
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
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server !!"+userId));
    }
}
