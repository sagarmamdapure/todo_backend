package com.example.todospringboot.service;


import com.example.todospringboot.entity.User;

import java.util.Optional;

public interface UserRepositoryService {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void save(User user);
}
