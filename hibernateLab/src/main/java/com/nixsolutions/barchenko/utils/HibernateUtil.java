package com.nixsolutions.barchenko.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public void closeResource(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public void rollbackAndClose(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

}
