package com.nixsolutions.barchenko.dao;

import com.nixsolutions.barchenko.entity.User;

import java.util.List;

public interface UserDao {
    void create(User user);

    void update(User user);

    void remove(User user);

    List findAll();

    User findByLogin(String login);

    User findByUserId(Long id);
}
