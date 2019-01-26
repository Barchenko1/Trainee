package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.UserDao;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HibernateUserDao implements UserDao {

    private HibernateUtil hibernateUtil = new HibernateUtil();

    private SessionFactory sessionFactory =
            hibernateUtil.getSessionFactory();

    private static final String SQL_USER_CREATE = "INSERT INTO USER (LOGIN, "
            + "PASSWORD, EMAIL, FIRSTNAME, LASTNAME, BIRTHDAY, roleId ) "
            + "VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_USER_UPDATE = "UPDATE USER SET "
            + "LOGIN = ?, PASSWORD = ?, EMAIL = ?, FIRSTNAME = ?, "
            + "LASTNAME = ?, BIRTHDAY = ?, roleId = ?  WHERE userId = ?";
    private static final String SQL_USER_REMOVE = "DELETE FROM USER "
            + "WHERE userId = ?";
    private static final String SQL_USER_FIND_ALL = "SELECT * FROM USER";
    private static final String SQL_USER_FIND_BY_LOGIN = "SELECT * FROM USER "
            + "WHERE LOGIN = ?";
    private static final String SQL_USER_FIND_BY_EMAIL = "SELECT * FROM USER "
            + "WHERE EMAIL = ?";

    private static final String HIBERNATE_HQL_SELECT_USER_BY_ID =
            "FROM User user WHERE user.userId =: userId";

    private static final String HIBERNATE_HQL_SELECT_USER_BY_LOGIN =
            "FROM User user WHERE user.login =: login";

    private static final String HIBERNATE_HQL_SELECT_USER_BY_EMAIL =
            "FROM User user WHERE user.email =: email";

    @Override
    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("create user fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("update user fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("remove user fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
    }


    @Override
    public List<User> findAll() {
        List<User> userList = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            userList = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("findAll user fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
        return userList;
    }

    @Override
    public User findByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        User user = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createQuery(
                    HIBERNATE_HQL_SELECT_USER_BY_LOGIN);
            query.setString("login", login);
            user = (User) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("findByLogin user fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException();
        }
        User user = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createQuery(
                    HIBERNATE_HQL_SELECT_USER_BY_EMAIL);
            query.setString("email", email);
            user = (User) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("findByEmail user fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
        return user;
    }

}
