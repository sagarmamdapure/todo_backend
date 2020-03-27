package com.example.todospringboot.dao;

import com.example.todospringboot.entity.Role;
import com.example.todospringboot.models.ERole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryDaoImpl implements RoleRepositoryDao {
  final SessionFactory sessionFactory;

  public RoleRepositoryDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<Role> findByName(ERole name) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<Role> query = session.createQuery("from Role where name=:name", Role.class);
    query.setParameter("name", name);
    return Optional.ofNullable(query.uniqueResult());
  }
}
