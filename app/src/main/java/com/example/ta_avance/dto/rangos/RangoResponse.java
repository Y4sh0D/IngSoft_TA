package com.example.ta_avance.dto.rangos;

import java.util.List;

public class RangoResponse {
    private int status;
    private String message;
    private List<RangoDto> data;

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

    public List<RangoDto> getData() {
        return data;
    }

    public void setData(List<RangoDto> data) {
        this.data = data;
    }
}
