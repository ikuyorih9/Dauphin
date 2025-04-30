package com.dauphin.dauphin.dtos;

public class FriendshipCreateRequestDTO {
    String fromUsername;
    String toUsername;
    public FriendshipCreateRequestDTO(String fromUsername, String toUsername) {
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
    }
    public String getFromUsername() {
        return fromUsername;
    }
    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }
    public String getToUsername() {
        return toUsername;
    }
    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }
}
