package com.dauphin.dauphin.dtos;

import jakarta.persistence.Entity;

public class GrupoEntranceRequestDTO {
    String token;
    String username;

    public GrupoEntranceRequestDTO(String token, String username) {
        this.token = token;
        this.username = username;
    }
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
