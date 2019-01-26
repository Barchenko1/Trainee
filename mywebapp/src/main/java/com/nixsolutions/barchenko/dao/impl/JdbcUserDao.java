package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.AbstractJdbcDao;
import com.nixsolutions.barchenko.dao.EntityMapper;
import com.nixsolutions.barchenko.dao.UserDao;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.utils.Fields;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao extends AbstractJdbcDao implements UserDao {

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

    @Override
    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_USER_CREATE);
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, user.getEmail());
            ps.setString(i++, user.getFirstName());
            ps.setString(i++, user.getLastName());
            ps.setDate(i++, user.getBirthday());
            ps.setLong(i, user.getRole().getRoleId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("findById rollback user fail ", e1);
            }
            throw new RuntimeException("findById user fail ", e);
        } finally {
            closeResource(con, ps, null, null);
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_USER_UPDATE);
            int i = 1;
            //ps.setLong(i++, user.getUserId());
            ps.setString(i++, user.getLogin());
            ps.setString(i++, user.getPassword());
            ps.setString(i++, user.getEmail());
            ps.setString(i++, user.getFirstName());
            ps.setString(i++, user.getLastName());
            ps.setDate(i++, user.getBirthday());
            ps.setLong(i++, user.getRole().getRoleId());
            ps.setLong(i, user.getUserId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("update rollback user fail ",e1);
            }
            throw new RuntimeException("update user fail ", e);
        } finally {
            closeResource(con, ps, null, null);
        }
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_USER_REMOVE);
            ps.setLong(1, user.getUserId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("remove rollback user fail ",e1);
            }
            throw new RuntimeException("remove user fail ", e);
        } finally {
            closeResource(con, ps, null, null);
        }
    }


    @Override
    public List<User> findAll() {
        Connection con = null;
        List<User> userList = new ArrayList<>();
        UserMapper userMapper = new UserMapper();
        Statement st = null;
        ResultSet rs = null;
        try {
            con = createConnection();
            st = con.createStatement();
            rs = st.executeQuery(SQL_USER_FIND_ALL);
            while (rs.next()) {
                userList.add(userMapper.mapRow(rs));
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("findAll rollback user fail ",e1);
            }
            throw new RuntimeException("findAll user fail ", e);
        } finally {
            closeResource(con, null, st, rs);
        }
        return userList;
    }

    @Override
    public User findByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException();
        }
        User user = new User();
        UserMapper userMapper = new UserMapper();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_USER_FIND_BY_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = userMapper.mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException(
                        "findByLogin rollback user fail ",e1);
            }
            throw new RuntimeException("findByLogin user fail ", e);
        } finally {
            closeResource(con, ps, null, rs);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException();
        }
        User user = new User();
        UserMapper userMapper = new UserMapper();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_USER_FIND_BY_EMAIL);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = userMapper.mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException(
                        "findByEmail rollback user fail ",e1);
            }
            throw new RuntimeException("findByEmail user fail ", e);
        } finally {
            closeResource(con, ps, null, rs);
        }
        return user;
    }

    private static class UserMapper implements EntityMapper<User> {
        @Override public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setUserId(rs.getLong(Fields.USER_ID));
                user.setLogin(rs.getString(Fields.USER_NAME));
                user.setPassword(rs.getString(Fields.USER_PASSWORD));
                user.setEmail(rs.getString(Fields.USER_EMAIL));
                user.setFirstName(rs.getString(Fields.USER_FIRSTNAME));
                user.setLastName(rs.getString(Fields.USER_LASTNAME));
                user.setBirthday(rs.getDate(Fields.USER_BIRTHDAY));
                long role = rs.getLong(Fields.USER_ROLE);
                user.setRole(new JdbcRoleDao().findById(role));
                return user;
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

}
