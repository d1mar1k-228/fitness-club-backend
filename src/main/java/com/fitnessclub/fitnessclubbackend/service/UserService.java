package com.fitnessclub.fitnessclubbackend.service;

import com.fitnessclub.fitnessclubbackend.model.user.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    void deleteUser(Long id);
}