package com.example.ta_avance.dto.valoracion;

public class ValoracionSimpleResponse {
    private long status;
    private String message;
    private ValoracionDto data;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValoracionDto getData() { return data; }

    public void setData(ValoracionDto data) { this.data = data; }

}
