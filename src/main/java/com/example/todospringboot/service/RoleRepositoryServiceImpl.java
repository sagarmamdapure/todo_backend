package com.example.todospringboot.service;

import com.example.todospringboot.dao.RoleRepositoryDao;
import com.example.todospringboot.entity.Role;
import com.example.todospringboot.models.ERole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleRepositoryServiceImpl implements RoleRepositoryService {

  private final RoleRepositoryDao roleRepositoryDao;

  public RoleRepositoryServiceImpl(RoleRepositoryDao roleRepositoryDao) {
    this.roleRepositoryDao = roleRepositoryDao;
  }

  @Override
  @Transactional
  public Optional<Role> findByName(ERole name) {
    return roleRepositoryDao.findByName(name);
  }
}
