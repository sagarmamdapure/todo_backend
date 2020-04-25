package com.example.todospringboot.service;

import com.example.todospringboot.entity.User;
import com.example.todospringboot.models.ForgetUserPayload;

import java.net.URISyntaxException;
import java.util.Optional;

public interface UserRepositoryService {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  void save(User user);

  void update(User user);

  String forgetPassword(ForgetUserPayload forgetUserPayload) throws URISyntaxException;

  Boolean changePassword(ForgetUserPayload forgetUserPayload);
}
