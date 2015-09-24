package com.sighini.instachat.dto;

public class UserData {

    private Integer id;
    private String token;
    private String email;
    private String name;

    /**
    *
    * @return
    * The id
    */
    public Integer getId() {
    return id;
    }

    /**
    *
    * @param id
    * The id
    */
    public void setId(Integer id) {
    this.id = id;
    }

    /**
    *
    * @return
    * The token
    */
    public String getToken() {
    return token;
    }

    /**
    *
    * @param token
    * The token
    */
    public void setToken(String token) {
    this.token = token;
    }

    /**
    *
    * @return
    * The email
    */
    public String getEmail() {
    return email;
    }

    /**
    *
    * @param email
    * The email
    */
    public void setEmail(String email) {
    this.email = email;
    }

    /**
    *
    * @return
    * The name
    */
    public String getName() {
    return name;
    }

    /**
    *
    * @param name
    * The name
    */
    public void setName(String name) {
    this.name = name;
    }

}