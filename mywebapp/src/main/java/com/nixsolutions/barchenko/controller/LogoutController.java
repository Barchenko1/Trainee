package com.nixsolutions.barchenko.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/logoutService")
public class LogoutController extends HttpServlet {
    @Override protected void doGet(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

        httpServletRequest.getSession().invalidate();
        httpServletResponse.sendRedirect("/loginService");
    }
}
