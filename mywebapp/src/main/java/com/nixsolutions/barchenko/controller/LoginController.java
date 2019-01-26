package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.dao.impl.JdbcRoleDao;
import com.nixsolutions.barchenko.dao.impl.JdbcUserDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.utils.Fields;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/loginService")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(
                Fields.LOGIN_PAGE).forward(request, response);
    }


    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        JdbcRoleDao roleDao = new JdbcRoleDao();
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String forward = null;
        session.setAttribute("login",login);
        User user = new JdbcUserDao().findByLogin(login);
        Role role = user.getRole();

        if (login == null || password == null || login.isEmpty() || password
                .isEmpty() || !password.equals(user.getPassword())) {
            forward = Fields.ERROR_PAGE;
            response.sendRedirect(forward);
        } else {
            session.setAttribute("user", user);
            session.setAttribute("role", role);

            if (role.getName().equals("USER")) {
                forward = Fields.USER_PAGE;
                request.getRequestDispatcher(forward).forward(
                        request, response);
            }
            if (role.getName().equals("ADMIN")) {
                session.setAttribute("roles", roleDao.findAllRoles());
                forward = Fields.ADMIN_PAGE;
                request.getRequestDispatcher(forward).forward(
                        request, response);
            }
        }
    }
}