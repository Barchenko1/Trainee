package com.nixsolutions.barchenko.action;

import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.validation.Valid;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;


@ResultPath(value = "/WEB-INF/pages")
public class RegistrationAction extends ActionSupport {
    @Valid
    private User user;
    private String birthday;

    @Autowired
    private UserService userService;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Action(value = "regPost", results = {
            @Result(name = "success", location = "login.jsp"),
            @Result(name = "input", location = "registration.jsp")})
    public String regPost() {
        getUser().setBirthday(Date.valueOf(getBirthday()));
        getUser().setRole(new Role(2L, "USER"));
        try {
            userService.create(getUser());
        } catch (Exception e) {
            this.addActionError("wrong data!");
            return INPUT;
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        String response = ServletActionContext.getRequest()
                .getParameter("g-recaptcha-response");
        if (!chkCaptch(response)) {
            addActionError("Confirm that you are not a robot!");
        }
    }

    private boolean chkCaptch(String responce) {
        final String url = "https://www.google.com/recaptcha/api/siteverify";
        final String secret = "6LdBXn8UAAAAAO3R5XDGoBiR4vSPvPgSwCWxDWXl";
        final String userAgent = "Mozilla/5.0";

        if (responce == null || "".equals(responce)) {
            return false;
        }

        try {
            URL obj = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", userAgent);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=" + secret + "&response="
                    + responce;
            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode=" + responseCode);

            InputStream is = conn.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();


            System.out.println("Response: " + jsonObject);

            boolean success = jsonObject.getBoolean("success");
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
