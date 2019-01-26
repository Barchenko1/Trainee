package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.AbstractJdbcDao;
import com.nixsolutions.barchenko.dao.EntityMapper;
import com.nixsolutions.barchenko.dao.RoleDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.utils.Fields;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRoleDao extends AbstractJdbcDao implements RoleDao {

    private static final String SQL_SELECT_ROLE_BY_ID = "SELECT * FROM "
            + "role where roleId = ?";

    private static final String SQL_ROLE_CREATE = "INSERT INTO role "
            + "(NAME) VALUES (?)";
    private static final String SQL_UPDATE_ROLE = "UPDATE role SET "
            + "roleId = ?, name = ? WHERE roleId = ?";
    private static final String SQL_DELETE_ROLE = "DELETE FROM role WHERE "
            + "roleId = ?";
    private static final String SQL_SELECT_ROLE_BY_NAME = "SELECT * FROM "
            + "role where name = ?";

    @Override
    public void create(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role is null");
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_ROLE_CREATE);
            int i = 1;
            ps.setString(i,role.getName());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("create rollback role fail ", e1);
            }
            throw new RuntimeException("create role fail ", e);
        } finally {
            closeResource(con, ps, null, null);
        }
    }

    @Override
    public void update(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role is null");
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_UPDATE_ROLE);
            int i = 1;
            ps.setLong(i++, role.getRoleId());
            ps.setString(i++, role.getName());
            ps.setLong(i, role.getRoleId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("update rollback role fail ", e1);
            }
            throw new RuntimeException("update role fail ", e);
        } finally {
            closeResource(con, ps, null, null);
        }
    }

    @Override
    public void remove(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role is null");
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_DELETE_ROLE);
            ps.setLong(1, role.getRoleId());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("remove rollback role fail ", e1);
            }
            throw new RuntimeException("remove role fail ", e);
        } finally {
            closeResource(con, ps, null, null);
        }
    }

    @Override
    public Role findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        Role role = new Role();
        RoleMapper roleMapper = new RoleMapper();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_SELECT_ROLE_BY_NAME);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                role = roleMapper.mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("findByName rollback fail ", e1);
            }
            throw new RuntimeException("findByName role fail ", e);
        } finally {
            closeResource(con, ps, null, rs);
        }
        return role;
    }

    private static class RoleMapper implements EntityMapper<Role> {
        @Override
        public Role mapRow(ResultSet rs) {
            try {
                Role role = new Role();
                role.setRoleId(rs.getLong(Fields.ROLE_ID));
                role.setName(rs.getString(Fields.ROLE_NAME));
                return role;
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public Role findById(long roleId) {
        Role role = new Role();
        RoleMapper roleMapper = new RoleMapper();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = createConnection();
            ps = con.prepareStatement(SQL_SELECT_ROLE_BY_ID);
            ps.setLong(1, roleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                role = roleMapper.mapRow(rs);
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.addSuppressed(e);
                throw new RuntimeException("findById rollback role fail ", e1);
            }
            throw new RuntimeException("findById role fail ", e);
        } finally {
            closeResource(con, ps, null, rs);
        }
        return role;
    }
}
