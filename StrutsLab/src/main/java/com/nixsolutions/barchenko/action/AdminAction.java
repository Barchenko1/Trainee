package com.nixsolutions.barchenko.action;

import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.RoleService;
import com.nixsolutions.barchenko.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;

@ResultPath(value = "/WEB-INF/pages")
public class AdminAction extends ActionSupport {
    @Valid
    private User user;
    private String birthday;
    private String role;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    private String editLogin;
    private String deleteLogin;

    public List<User> getUsers() {
        return userService.findAll();
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String createdGet() {
        return SUCCESS;
    }

    @Action(value = "createPost", results = {
            @Result(name = "success", location = "/admin", type = "redirect"),
            @Result(name = "input", location = "/create", type = "redirect")})
    public String createdPost() {

        if (user != null) {
            if (!checkUser(getUser())) {
                this.addActionError("This user is exist");
                return INPUT;
            }
        }

        getUser().setBirthday(Date.valueOf(getBirthday()));
        getUser().setRole(roleService.findById(Long.valueOf(role)));
        try {
            userService.create(user);
        } catch (RuntimeException e) {
            this.addActionError("wrong data!");
            return INPUT;
        }
        return SUCCESS;
    }



    public String editGet() {
        user = userService.findByLogin(getEditLogin());
        return SUCCESS;
    }

    @Action(value = "editPost", results = {
            @Result(name = "success", location = "/admin", type = "redirect"),
            @Result(name = "input", location = "/edit", type = "redirect")})
    public String editPost() {

        Long userId = userService.findByLogin(getUser().getLogin()).getUserId();
        try {
            getUser().setUserId(userId);
            getUser().setRole(roleService.findById(Long.valueOf(role)));
            getUser().setBirthday(Date.valueOf(getBirthday()));
            userService.update(getUser());
        } catch (Exception e) {
            this.addActionError("wrong data!");
            return INPUT;
        }
        return SUCCESS;
    }

    public String deleteGet() {
        user = userService.findByLogin(getDeleteLogin());
        userService.remove(user);
        return SUCCESS;
    }

    private boolean checkUser(User user) {
        for (User u : getUsers()) {
            if (u.getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        return true;
    }



    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEditLogin() {
        return editLogin;
    }

    public void setEditLogin(String editLogin) {
        this.editLogin = editLogin;
    }

    public String getDeleteLogin() {
        return deleteLogin;
    }

    public void setDeleteLogin(String deleteLogin) {
        this.deleteLogin = deleteLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }




}
