package com.example.todospringboot.dao;

import com.example.todospringboot.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class UserRepositoryDaoImpl implements UserRepositoryDao {
    final SessionFactory sessionFactory;
    private Logger logger = Logger.getLogger(getClass().getName());

    public UserRepositoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User where username=:userName", User.class);
        query.setParameter("userName", userName);
        return Optional.ofNullable(query.uniqueResult());
    }

    @Override
    public Boolean existsByUsername(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Integer> query =
                session.createQuery("select 1 from User where username=:userName", Integer.class);
        query.setParameter("userName", userName);
        return (query.uniqueResult() != null);
    }

    @Override
    public Boolean existsByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Integer> query =
                session.createQuery("select 1 from User where email=:email", Integer.class);
        query.setParameter("email", email);
        return (query.uniqueResult() != null);
    }

    @Override
    public void save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }
}
