package com.example.ta_avance.dto;

public class Barbero {
    private int barbero_id;
    private String nombre;
    private int estado;

    public int getBarbero_id() {
        return barbero_id;
    }

    public void setBarbero_id(int barbero_id) {
        this.barbero_id = barbero_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}