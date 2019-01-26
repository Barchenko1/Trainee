package com.nixsolutions.barchenko.tag;

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

    private List<User> users;

    @Override
    public int doStartTag() throws JspException {

        pageContext.getRequest().getServerName();

        JspWriter out = pageContext.getOut();

        StringBuilder sb = new StringBuilder();

        sb.append("<table class=\"" + "" + "\">");
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
                    .getContextPath() + "/edit?editLogin="
                    + user.getLogin() + "\" >Edit</a>");
            sb.append("  <a href=\""
                    + ((HttpServletRequest) pageContext.getRequest())
                    .getContextPath() + "/delete?deleteLogin="
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

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
