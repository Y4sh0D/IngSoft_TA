package com.example.ta_avance.dto.reserva;

public class ReservasRequest {
    private final int id_reserva;
    private final String hora;
    private final String barbero;
    private final String servicio;
    private final int costo;

    public ReservasRequest(int idReserva, String hora, String barbero, String servicio, int costo) {
        id_reserva = idReserva;
        this.hora = hora;
        this.barbero = barbero;
        this.servicio = servicio;
        this.costo = costo;
    }

    public int getId_reserva() { return  id_reserva; }

    public String getHora() { return hora; }

    public String getBarbero() {
        return barbero;
    }

    public String getServicio() {
        return servicio;
    }

    public int getCosto() {
        return costo;
    }
}
