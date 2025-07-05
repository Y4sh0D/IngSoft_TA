package com.example.ta_avance.dto.reserva;

import java.util.List;

public class DtoReservaResponse {
    private long status;
    private String message;
    private List<DtoReserva> data;

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

    public List<DtoReserva> getData() {
        return data;
    }

    public void setData(List<DtoReserva> data) {
        this.data = data;
    }
}
