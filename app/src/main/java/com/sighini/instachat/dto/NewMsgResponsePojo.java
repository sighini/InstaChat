package com.sighini.instachat.dto;


public class NewMsgResponsePojo {


    private Boolean success;

    private MsgUserData data;

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return The data
     */
    public MsgUserData getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(MsgUserData data) {
        this.data = data;
    }

}