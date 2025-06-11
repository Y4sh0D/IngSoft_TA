package com.example.ta_avance.dto.servicio;

import java.util.List;

public class   ServicioResponse {
    private int status;
    private String message;
    private List<ServicioDto> data;

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

    public List<ServicioDto> getData() {
        return data;
    }

    public void setData(List<ServicioDto> data) {
        this.data = data;
    }
}
