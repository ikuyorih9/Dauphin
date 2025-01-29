package com.dauphin.dauphin.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private HttpStatus status; // Alterado de String para HttpStatus
    private String message;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getter para o campo status
    public HttpStatus getStatus() {
        return status;
    }

    // Setter para o campo status
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    // Getter para o campo message
    public String getMessage() {
        return message;
    }

    // Setter para o campo message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter para o campo data
    public T getData() {
        return data;
    }

    // Setter para o campo data
    public void setData(T data) {
        this.data = data;
    }
}
