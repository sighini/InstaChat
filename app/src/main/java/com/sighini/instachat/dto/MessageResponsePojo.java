package com.sighini.instachat.dto;

import java.util.ArrayList;
import java.util.List;

public class MessageResponsePojo {

    private Boolean success;
    private List<MsgUserData> data = new ArrayList<MsgUserData>();
    private Pagination pagination;

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
    public List<MsgUserData> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<MsgUserData> data) {
        this.data = data;
    }

    /**
     * @return The pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * @param pagination The pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}