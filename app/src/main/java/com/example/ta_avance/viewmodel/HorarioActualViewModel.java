package com.example.ta_avance.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class HorarioActualViewModel extends ViewModel {
    private final MutableLiveData<Map<String, Map<String, String>>> horarios = new MutableLiveData<>();

    public HorarioActualViewModel() {
        // Datos simulados por ahora
        Map<String, Map<String, String>> semana = new HashMap<>();
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        String[] turnos = {"Mañana", "Tarde", "Noche"};

        for (String dia : dias) {
            Map<String, String> turnosDia = new HashMap<>();
            turnosDia.put("Mañana", "Juan, Pedro");
            turnosDia.put("Tarde", "Luis");
            turnosDia.put("Noche", "Carlos");
            semana.put(dia, turnosDia);
        }

        horarios.setValue(semana);
    }

    public LiveData<Map<String, Map<String, String>>> getHorarios() {
        return horarios;
    }
}