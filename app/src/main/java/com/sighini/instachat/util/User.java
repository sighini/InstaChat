package com.sighini.instachat.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class User {

    private String email;
    private String name;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostParameters(){
        StringBuilder result = new StringBuilder();
        try {
            result.append(URLEncoder.encode("email", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(getEmail(), "UTF-8"));
            result.append("&");

            result.append(URLEncoder.encode("name", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(getName(), "UTF-8"));
            result.append("&");

            result.append(URLEncoder.encode("password", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(getPassword(), "UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
