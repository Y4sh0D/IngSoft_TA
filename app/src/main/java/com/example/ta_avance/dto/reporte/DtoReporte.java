package com.example.ta_avance.dto.reporte;

public class DtoReporte {
    private String servicioNombre;
    private long montoTotal;
    private long cantidadReservas;

    public String getServicioNombre() {
        return servicioNombre;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }

    public long getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(long montoTotal) {
        this.montoTotal = montoTotal;
    }

    public long getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(long cantidadReservas) {
        this.cantidadReservas = cantidadReservas;
    }
}
