package com.sighini.instachat.dto;

import java.util.ArrayList;
import java.util.List;

public class ChatResponsePojo {

    private Boolean success;

    private List<ChatUserData> data = new ArrayList<ChatUserData>();

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
    public List<ChatUserData> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<ChatUserData> data) {
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