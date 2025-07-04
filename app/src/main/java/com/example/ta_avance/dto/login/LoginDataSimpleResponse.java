package com.example.ta_avance.dto.login;

public class LoginDataSimpleResponse {
    private String status;
    private String message;
    private LoginData data;  // Esta es la nueva clase que contendrá el token y el rol

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
