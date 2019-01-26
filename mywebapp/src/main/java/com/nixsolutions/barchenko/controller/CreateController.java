package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.dao.impl.JdbcRoleDao;
import com.nixsolutions.barchenko.dao.impl.JdbcUserDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/create")
public class CreateController extends HttpServlet {

    private JdbcUserDao userDao;

    private boolean containsLogin(String login) {
        for (User u: userDao.findAll()) {
            if (u.getLogin().equals(login)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordAgain = req.getParameter("passwordAgain");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        Date date = Date.valueOf(req.getParameter("date"));
        Role role = new JdbcRoleDao().findById(
                Long.parseLong(req.getParameter("role")));
        userDao = new JdbcUserDao();

        User user = new User(login, password, email, firstName, lastName, date,
                role);

        if (password.equals(passwordAgain) && containsLogin(login)) {
            userDao.create(user);
        }

        resp.sendRedirect("adminAccount.jsp");
    }

}
