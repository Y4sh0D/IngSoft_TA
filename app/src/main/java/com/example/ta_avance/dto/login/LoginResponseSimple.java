package com.example.ta_avance.dto.login;

public class LoginResponseSimple {
    private int status;
    private String message;
    private LoginRequest data;

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

    public LoginRequest getData() {
        return data;
    }

    public void setData(LoginRequest data) {
        this.data = data;
    }
}
