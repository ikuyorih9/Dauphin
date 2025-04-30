package com.dauphin.dauphin.dtos;

public class GroupNewUserDTO {
    String username;
    Integer id;
    String role;

    public GroupNewUserDTO(String username, Integer groupId, String role) {
        this.username = username;
        this.id = groupId;
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
