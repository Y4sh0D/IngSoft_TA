package com.example.ta_avance.dto.reporte;

public class DtoReporteResponse {
    private long status;
    private String message;
    private DtoReporte data;

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

    public DtoReporte getData() {
        return data;
    }

    public void setData(DtoReporte data) {
        this.data = data;
    }
}
