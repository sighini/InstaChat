package com.sighini.instachat.dto;

public class MsgUserData {

    private Integer id;
    private Integer chatId;
    private Integer user_id;
    private String message;
    private MsgUser user;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The chatId
     */
    public Integer getChatId() {
        return chatId;
    }

    /**
     * @param chatId The chat_id
     */
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    /**
     * @return The user_id
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * @param user_id The user_id
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The user
     */
    public MsgUser getUser() {
        return user;
    }

    /**
     * @param msgUser The msgUser
     */
    public void setUser(MsgUser msgUser) {
        this.user = msgUser;
    }

    @Override
    public String toString() {
        return "MsgUserData{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", user_id=" + user_id +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
