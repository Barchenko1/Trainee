package com.nixsolutions.barchenko.service;

import com.nixsolutions.barchenko.entity.User;

import java.util.List;

public interface UserService {
    void create(User user);

    void update(User user);

    void remove(User user);

    List<User> findAll();

    User findByLogin(String login);

    User findByUserId(Long id);
}
