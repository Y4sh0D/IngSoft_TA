package com.example.ta_avance.dto.reserva;

public class DtoReserva {
    private long reservaId;
    private String barberoNombre;
    private String usuarioNombre;
    private long usuarioId;
    private String horarioRango;
    private String estado;
    private String motivoDescripcion;
    private String adicionales;
    private String fechaCreacion;
    private String servicioNombre;
    private long precioServicio;
    private String fechaReserva;
    private String urlPago;
    private long montoTotal;

    public long getReservaId() { return reservaId; }

    public void setReservaId(long reservaId) { this.reservaId = reservaId; }

    public String getBarberoNombre() { return barberoNombre; }

    public void setBarberoNombre(String barberoNombre) { this.barberoNombre = barberoNombre; }

    public String getUsuarioNombre() { return usuarioNombre; }

    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public long getUsuarioId() { return usuarioId; }

    public void setUsuarioId(long usuarioId) { this.usuarioId = usuarioId; }

    public String getHorarioRango() { return horarioRango; }

    public void setHorarioRango(String horarioRango) { this.horarioRango = horarioRango; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    public String getMotivoDescripcion() { return motivoDescripcion; }

    public void setMotivoDescripcion(String motivoDescripcion) { this.motivoDescripcion = motivoDescripcion; }

    public String getAdicionales() { return adicionales; }

    public void setAdicionales(String adicionales) { this.adicionales = adicionales; }

    public String getFechaCreacion() { return fechaCreacion; }

    public void setFechaCreacion(String fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getServicioNombre() { return servicioNombre; }

    public void setServicioNombre(String servicioNombre) { this.servicioNombre = servicioNombre; }

    public long getPrecioServicio() { return precioServicio; }

    public void setPrecioServicio(long precioServicio) { this.precioServicio = precioServicio; }

    public String getFechaReserva() { return fechaReserva; }

    public void setFechaReserva(String fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getUrlPago() { return urlPago; }

    public void setUrlPago(String urlPago) { this.urlPago = urlPago; }

    public long getMontoTotal() { return montoTotal; }

    public void setMontoTotal(long montoTotal) { this.montoTotal = montoTotal; }
}
