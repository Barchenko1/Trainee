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


@WebServlet("/update")
public class UpdateController extends HttpServlet {

    private JdbcUserDao userDao;

    @Override protected void doGet(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        userDao = new JdbcUserDao();
        JdbcRoleDao roleDao = new JdbcRoleDao();

        String loginToEdit = req.getParameter("login");
        User user = userDao.findByLogin(loginToEdit);

        req.setAttribute("editLogin", user.getLogin());
        req.setAttribute("editPassword", user.getPassword());
        req.setAttribute("editFirstname", user.getFirstName());
        req.setAttribute("editLastname", user.getLastName());
        req.setAttribute("editEmail", user.getEmail());
        req.setAttribute("editBirthday", user.getBirthday());

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
        Role role = new JdbcRoleDao().findById(
                Long.parseLong(req.getParameter("role")));
        userDao = new JdbcUserDao();

        Long userId = new JdbcUserDao().findByLogin(login).getUserId();

        User user = new User(userId, login, password, email, firstName,
                lastName, date, role);

        if (password.equals(passwordAgain)) {
            userDao.update(user);
        }

        resp.sendRedirect("adminAccount.jsp");
    }
}