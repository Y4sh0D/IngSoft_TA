package com.example.ta_avance.dto.rangos;

public class RangoDto {
    private int horarioRango_id;
    private String rango;
    private String tipoHorario;

    public int getHorarioRango_id() {
        return horarioRango_id;
    }

    public void setHorarioRango_id(int horarioRango_id) {
        this.horarioRango_id = horarioRango_id;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public String getTipoHorario() {
        return tipoHorario;
    }

    public void setTipoHorario(String tipoHorario) {
        this.tipoHorario = tipoHorario;
    }
}
