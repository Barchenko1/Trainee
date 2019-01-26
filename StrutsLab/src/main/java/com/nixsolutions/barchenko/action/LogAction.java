package com.nixsolutions.barchenko.action;

import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ResultPath(value = "/WEB-INF/pages")
public class LogAction extends ActionSupport implements SessionAware {
    @Valid
    private User user;

    @Autowired
    private UserService userService;
    private SessionMap<String, Object> sessionMap;

    public List<User> getUsers() {
        return userService.findAll();
    }

    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMap = (SessionMap<String, Object>) map;
    }

    public String login() {

        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        User user = userService.findByLogin(auth.getName());
        sessionMap.put("firstName", user.getFirstName());
        sessionMap.put("lastName", user.getLastName());
        if (user.getRole().getRoleId().equals(1L)) {
            return "admin";
        } else if (user.getRole().getRoleId().equals(2L)) {
            return "user";
        }
        return "login";
    }

    public String logout() {
        sessionMap.invalidate();
        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
