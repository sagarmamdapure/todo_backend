package com.example.todospringboot.service;

import com.example.todospringboot.entity.Role;
import com.example.todospringboot.models.ERole;

import java.util.Optional;

public interface RoleRepositoryService {
  Optional<Role> findByName(ERole name);
}
