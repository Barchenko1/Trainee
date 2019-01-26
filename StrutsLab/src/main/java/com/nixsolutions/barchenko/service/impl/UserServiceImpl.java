package com.nixsolutions.barchenko.service.impl;

import com.nixsolutions.barchenko.dao.UserDao;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void create(User user) {
        userDao.create(user);
    }

    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    public void remove(User user) {
        userDao.remove(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

}

