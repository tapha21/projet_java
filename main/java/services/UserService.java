package services;

import java.util.List;

import entities.User;


public interface UserService {
       void createClient(User user);
        List<User> findAll();
        User search(String tel);
}
