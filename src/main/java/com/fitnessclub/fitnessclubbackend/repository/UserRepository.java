package com.fitnessclub.fitnessclubbackend.repository;

import com.fitnessclub.fitnessclubbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Найти пользователя по email
     */
    Optional<User> findByEmail(String email);

    /**
     * Найти пользователей по роли
     */
    java.util.List<User> findByRole(com.fitnessclub.fitnessclubbackend.model.user.UserRole role);
}