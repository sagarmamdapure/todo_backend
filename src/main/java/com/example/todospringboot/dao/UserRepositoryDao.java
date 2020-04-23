package com.example.todospringboot.dao;

import com.example.todospringboot.entity.User;

import java.util.Optional;

public interface UserRepositoryDao {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void save(User user);

    void update(User user);
}
