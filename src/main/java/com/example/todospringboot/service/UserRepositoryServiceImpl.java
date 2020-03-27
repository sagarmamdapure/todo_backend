package com.example.todospringboot.service;

import com.example.todospringboot.dao.UserRepositoryDao;
import com.example.todospringboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UserRepositoryServiceImpl implements UserRepositoryService {
    @Autowired
    UserRepositoryDao userRepositoryDao;

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepositoryDao.findByUsername(username);
    }

    @Override
    @Transactional
    public Boolean existsByUsername(String username) {
        return userRepositoryDao.existsByUsername(username);
    }

    @Override
    @Transactional
    public Boolean existsByEmail(String email) {
        return userRepositoryDao.existsByEmail(email);
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepositoryDao.save(user);
    }
}
