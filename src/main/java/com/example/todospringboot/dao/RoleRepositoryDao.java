package com.example.todospringboot.dao;


import com.example.todospringboot.entity.Role;
import com.example.todospringboot.models.ERole;

import java.util.Optional;

public interface RoleRepositoryDao {
    Optional<Role> findByName(ERole name);
}