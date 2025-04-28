package com.example.ta_avance.modelo;

public class LoginResponse {
    private String status;
    private String message;
    private LoginData data;  // Esta es la nueva clase que contendrá el token y el rol

    public static class LoginData {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
