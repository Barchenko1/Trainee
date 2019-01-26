package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.RoleDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRoleDao implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Role role) {
        Role findRole = findByName(role.getName());
        if (findRole != null) {
            throw new IllegalArgumentException(role.getName());
        }
        try {
            sessionFactory.getCurrentSession().save(role);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Role role) {
        if (findByName(role.getName()) != null) {
            throw new RuntimeException(role.getName());
        }
        try {
            sessionFactory.getCurrentSession().clear();
            sessionFactory.getCurrentSession().update(role);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Role role) {
        if (findByName(role.getName()) == null) {
            throw new RuntimeException(role.toString());
        }
        try {
            sessionFactory.getCurrentSession().clear();
            sessionFactory.getCurrentSession().remove(role);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Role findByName(String name) {
        String hql = "FROM Role role WHERE role.name =: name";
        Role role;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("name", name);
        if (query.list().isEmpty()) {
            return null;
        }
        role = (Role) query.getSingleResult();
        return role;
    }

    @Override
    public List findAll() {
        String hql = "FROM Role";
        List<User> users;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (query.list().isEmpty()) {
            return null;
        }
        users = query.list();
        return users;
    }

    @Override public Role findById(Long roleId) {
        String hql = "FROM Role role WHERE role.roleId =: roleId";
        Role role;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("roleId", roleId);
        if (query.list().isEmpty()) {
            return null;
        }
        role = (Role) query.getSingleResult();
        return role;
    }
}
