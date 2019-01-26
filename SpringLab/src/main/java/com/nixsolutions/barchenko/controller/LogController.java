package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.RoleService;
import com.nixsolutions.barchenko.service.UserService;
import com.nixsolutions.barchenko.utils.Fields;
import java.security.Principal;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET, value = { "login", "/" })
    public String loginPage(HttpSession session) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/enter")
    public ModelAndView login(
            Principal principal, HttpSession session) {
        String login = principal.getName();
        User user = userService.findByLogin(login);
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        if (user.getRole().getRoleId() == 2L) {
            return new ModelAndView("redirect:/" + Fields.USER_PAGE);
        }
        return new ModelAndView("redirect:/" + Fields.ADMIN_PAGE);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/" + Fields.LOGIN_PAGE;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/error")
    public String errorPage() {
        return "redirect:/" + Fields.ERROR_PAGE;
    }
}
