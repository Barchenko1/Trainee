package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.RoleDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HibernateRoleDao implements RoleDao {

    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory =
            hibernateUtil.getSessionFactory();


    private static final String HIBERNATE_HQL_SELECT_ROLE_BY_ID =
            "FROM Role role WHERE role.roleId =: roleId";

    private static final String HIBERNATE_HQL_SELECT_ROLE_BY_NAME =
            "FROM Role role WHERE role.name =: name";

    @Override
    public void create(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.save(role);
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException(e);
        } finally {
            hibernateUtil.closeResource(session);
        }
    }

    @Override
    public void update(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.update(role);
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("update role fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
    }

    @Override
    public void remove(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("role is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.delete(role);
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("remove role fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
    }

    @Override
    public Role findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        Role role = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createQuery(
                    HIBERNATE_HQL_SELECT_ROLE_BY_NAME);
            query.setString("name", name);
            role = (Role) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("findByName role fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
        return role;
    }

    public Role findById(long roleId) {
        Role role = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createQuery(HIBERNATE_HQL_SELECT_ROLE_BY_ID);
            query.setLong("roleId", roleId);
            role = (Role) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("findById role fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
        return role;
    }

    public List<Role> findAllRoles() {
        List<Role> roleList = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            roleList = session.createCriteria(Role.class).list();
            transaction.commit();
        } catch (Exception e) {
            hibernateUtil.rollbackAndClose(transaction);
            throw new RuntimeException("findAllRoles role fail ", e);
        } finally {
            hibernateUtil.closeResource(session);
        }
        return roleList;
    }
}
