package com.dauphin.dauphin.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TemAmizadeId implements Serializable{
    private String username1;
    private String username2;

    public TemAmizadeId(){}

    public TemAmizadeId(String username1, String username2){
        this.username1 = username1;
        this.username2 = username2;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemAmizadeId)) return false;
        TemAmizadeId that = (TemAmizadeId) o;
        return Objects.equals(username1, that.username1) &&
               Objects.equals(username2, that.username2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username1, username2);
    }
}