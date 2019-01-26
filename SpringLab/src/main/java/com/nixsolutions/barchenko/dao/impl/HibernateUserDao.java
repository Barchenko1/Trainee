package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.UserDao;
import com.nixsolutions.barchenko.entity.User;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(User user) {
        if (findByLogin(user.getLogin()) != null) {
            throw new IllegalArgumentException(user.getLogin());
        }
        try {
            sessionFactory.getCurrentSession().save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        if (findByLogin(user.getLogin()) == null) {
            throw new RuntimeException(user.getLogin());
        }
        try {
            sessionFactory.getCurrentSession().clear();
            sessionFactory.getCurrentSession().update(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(User user) {
        if (findByLogin(user.getLogin()) == null) {
            throw new RuntimeException(user.getLogin());
        }
        try {
            sessionFactory.getCurrentSession().clear();
            sessionFactory.getCurrentSession().remove(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {
        User user;
        String hql = "FROM User where login =: login";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("login", login);
        if (query.list().isEmpty()) {
            return null;
        }
        user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public List<User> findAll() {
        String hql = "FROM User";
        List<User> users;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (query.list().isEmpty()) {
            return null;
        }
        users = query.list();
        return users;
    }



}

