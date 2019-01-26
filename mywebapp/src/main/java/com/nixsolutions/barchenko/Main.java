package com.nixsolutions.barchenko;

import com.nixsolutions.barchenko.dao.impl.JdbcRoleDao;
import com.nixsolutions.barchenko.dao.impl.JdbcUserDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import java.util.List;

//chmod +x /home/tran/Database/h2/bin/h2.sh
// chmod +x h2.sh
//http://127.0.1.1:8082

public class Main {

    public static void main(String[] args) {
        List<User> users = new JdbcUserDao().findAll();
        Role role = new JdbcRoleDao().findById(1);
        StringBuilder str = new StringBuilder();
        for (User user:users) {
            System.out.print(user.getLogin() + " ");
            System.out.print(user.getFirstName() + " ");
            System.out.print(user.getLastName() + " ");
            System.out.print(user.getBirthday() + " ");
            System.out.print(user.getEmail() + " ");
            System.out.print(user.getRole().getName() + " ");
            System.out.println(user.getRole());
            System.out.println();
        }
        System.out.println(role.getName());
    }
}
