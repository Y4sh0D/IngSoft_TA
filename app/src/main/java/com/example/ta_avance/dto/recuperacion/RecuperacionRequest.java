package com.example.ta_avance.dto.recuperacion;

public class RecuperacionRequest {
    private String username;
    private String mailTo; // CAMBIO AQUÍ

    public RecuperacionRequest(String username, String mailTo) {
        this.username = username;
        this.mailTo = mailTo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }
}
