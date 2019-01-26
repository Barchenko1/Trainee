package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.dao.impl.JdbcUserDao;
import com.nixsolutions.barchenko.entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class DeleteController extends HttpServlet {

    private JdbcUserDao userDao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        User user = userDao.findByLogin(login);
        userDao.remove(user);
        resp.sendRedirect("adminAccount.jsp");
    }
}
