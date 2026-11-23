package com.fitnessclub.fitnessclubbackend.service;

import com.fitnessclub.fitnessclubbackend.exception.BadRequestException;
import com.fitnessclub.fitnessclubbackend.model.user.User;
import com.fitnessclub.fitnessclubbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

        @Override
    public User createUser(User user) {
        boolean emailExists = userRepository.findAll().stream()
                .anyMatch(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(user.getEmail()));

        if (emailExists) {
            throw new BadRequestException("Пользователь с email " + user.getEmail() + " уже существует");
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}