package com.nixsolutions.barchenko;

import com.nixsolutions.barchenko.dao.impl.HibernateRoleDao;
import com.nixsolutions.barchenko.dao.impl.HibernateUserDao;


//chmod +x /home/tran/Database/h2/bin/h2.sh
// chmod +x h2.sh
//http://127.0.1.1:8082

public class Main {

    public static void main(String[] args) {
        HibernateRoleDao hibernateRoleDao = new HibernateRoleDao();
        HibernateUserDao hibernateUserDao = new HibernateUserDao();
        System.out.println(hibernateRoleDao.findAllRoles());
    }
}
