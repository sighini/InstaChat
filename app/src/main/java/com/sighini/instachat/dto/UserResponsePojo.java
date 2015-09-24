package com.sighini.instachat.dto;

public class UserResponsePojo {

    private Boolean success;
    private UserData data;
    private Error error;

    /**
    *
    * @return
    * The success
    */
    public Boolean getSuccess() {
    return success;
    }

    /**
    *
    * @param success
    * The success
    */
    public void setSuccess(Boolean success) {
    this.success = success;
    }

    /**
    *
    * @return
    * The data
    */
    public UserData getData() {
    return data;
    }

    /**
    *
    * @param data
    * The data
    */
    public void setData(UserData data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The error
     */
    public Error getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(Error error) {
        this.error = error;
    }
}