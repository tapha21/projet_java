package com.services;

import java.util.List;

import com.entities.User;


public interface UserService {
       void createClient(User user);
        List<User> findAll();
        User search(String tel);
}
