package com.nixsolutions.barchenko.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginPassword {

    private String username;

    private String password;

    public LoginPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginPassword() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
