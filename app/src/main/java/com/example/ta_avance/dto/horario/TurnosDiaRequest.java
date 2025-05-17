package com.example.ta_avance.dto.horario;

import java.util.Map;
import java.util.List;

public class TurnosDiaRequest {
    private String dia;
    private Map<Long, List<Long>> turnosPorTipo;

    public TurnosDiaRequest(String dia, Map<Long, List<Long>> turnosPorTipo) {
        this.dia = dia;
        this.turnosPorTipo = turnosPorTipo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Map<Long, List<Long>> getTurnosPorTipo() {
        return turnosPorTipo;
    }

    public void setTurnosPorTipo(Map<Long, List<Long>> turnosPorTipo) {
        this.turnosPorTipo = turnosPorTipo;
    }
}
