package com.sheildog.csleevebackend.service;

import com.sheildog.csleevebackend.model.User;
import com.sheildog.csleevebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id){
        return userRepository.findFirstById(id);
    }
}
