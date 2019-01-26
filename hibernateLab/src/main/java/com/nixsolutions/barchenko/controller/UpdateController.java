package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.dao.impl.HibernateRoleDao;
import com.nixsolutions.barchenko.dao.impl.HibernateUserDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/update")
public class UpdateController extends HttpServlet {

    private HibernateUserDao userDao;
    private HibernateRoleDao roleDao;

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("edit.jsp").forward(req, resp);

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
        Role role = new HibernateRoleDao().findByName(req.getParameter("role"));
        userDao = new HibernateUserDao();

        Long userId = new HibernateUserDao().findByLogin(login).getUserId();

        User user = new User(userId, login, password, email, firstName,
                lastName, date, role);

        if (password.equals(passwordAgain)) {
            userDao.update(user);
        }

        resp.sendRedirect("adminAccount.jsp");
    }
}