package com.example.todospringboot.service;

import com.example.todospringboot.dao.RoleRepositoryDao;
import com.example.todospringboot.entity.Role;
import com.example.todospringboot.models.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class RoleRepositoryServiceImpl implements RoleRepositoryService {

  @Autowired
  private RoleRepositoryDao roleRepositoryDao;

  @Override
  @Transactional
  public Optional<Role> findByName(ERole name) {
    return roleRepositoryDao.findByName(name);
  }
}
