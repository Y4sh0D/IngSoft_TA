package com.example.ta_avance.dto.login;

import java.util.List;

public class LoginResponse {
    private int status;
    private String message;
    private List<LoginRequest> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LoginRequest> getData() {
        return data;
    }

    public void setData(List<LoginRequest> data) {
        this.data = data;
    }
}
