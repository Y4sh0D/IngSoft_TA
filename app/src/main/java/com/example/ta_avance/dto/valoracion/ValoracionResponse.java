package com.example.ta_avance.dto.valoracion;

import java.util.List;

public class ValoracionResponse {
    private long status;
    private String message;
    private List<ValoracionDto> data;

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

    public List<ValoracionDto> getData() {
        return data;
    }

    public void setData(List<ValoracionDto> data) {
        this.data = data;
    }
}
