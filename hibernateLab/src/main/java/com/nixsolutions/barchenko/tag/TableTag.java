package com.nixsolutions.barchenko.tag;

import com.nixsolutions.barchenko.dao.impl.HibernateRoleDao;
import com.nixsolutions.barchenko.dao.impl.HibernateUserDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TableTag extends TagSupport {

    private String empty;

    private List<User> users;
    private List<Role> roles;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int doStartTag() throws JspException {

        users = new HibernateUserDao().findAll();
        roles = new HibernateRoleDao().findAllRoles();

        pageContext.getRequest().getServerName();

        JspWriter out = pageContext.getOut();

        StringBuilder sb = new StringBuilder();

        sb.append("<table class=\"" + empty + "\">");
        sb.append("<tr><td><h3>");
        sb.append("Login");
        sb.append("</h3></td>");
        sb.append("<td><h3>");
        sb.append("First Name");
        sb.append("</h3></td>");
        sb.append("<td><h3>");
        sb.append("Last Name");
        sb.append("</h3></td>");
        sb.append("<td><h3>");
        sb.append("Age");
        sb.append("</h3></td>");
        sb.append("<td><h3>");
        sb.append("Role");
        sb.append("</h3></td>");
        sb.append("<td><h3>");
        sb.append("Actions");
        sb.append("</h3></td></tr>");

        for (User user : users) {
            sb.append("<tr><td>");
            sb.append(user.getLogin());
            sb.append("</td><td>");
            sb.append(user.getFirstName());
            sb.append("</td><td>");
            sb.append(user.getLastName());
            sb.append("</td><td>");
            sb.append(findAge(user.getBirthday()));
            sb.append("</td><td>");
            sb.append(user.getRole().getName());
            sb.append("</td><td>");
            sb.append("<a href=\""
                    + ((HttpServletRequest) pageContext.getRequest())
                    .getContextPath() + "/update?login="
                    + user.getLogin() + "\" >Edit</a>");
            sb.append("  <a href=\""
                    + ((HttpServletRequest) pageContext.getRequest())
                    .getContextPath() + "/delete?login="
                    + user.getLogin()
                    + "\" onclick=\"return confirm('Are you sure?');"
                    + "\">Delete</a>");
            sb.append("</td></tr>");
        }
        sb.append("</table>");

        try {
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }

    private int findAge(Date firstDate) {
        Date lastDate = new Date(firstDate.getTime());
        LocalDate birthDate =
                lastDate.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now();
        if ((birthDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

}

