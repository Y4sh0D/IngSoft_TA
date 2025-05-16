package com.example.ta_avance.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

public class HorarioPrepararViewModel extends ViewModel {

    private final MutableLiveData<List<String>> dias = new MutableLiveData<>();
    private final MutableLiveData<List<String>> turnos = new MutableLiveData<>();
    private final MutableLiveData<List<String>> barberos = new MutableLiveData<>();

    public HorarioPrepararViewModel() {
        dias.setValue(Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        turnos.setValue(Arrays.asList("Mañana", "Tarde", "Noche"));
        barberos.setValue(Arrays.asList("Juan", "Pedro", "Luis", "Carlos"));
    }

    public LiveData<List<String>> getDias() {
        return dias;
    }

    public LiveData<List<String>> getTurnos() {
        return turnos;
    }

    public LiveData<List<String>> getBarberos() {
        return barberos;
    }
}
