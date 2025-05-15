package com.example.ta_avance.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ta_avance.dto.ReservasRequest;

public class ReservasPorConfirmarViewModel extends ViewModel {

    private final MutableLiveData<ReservasRequest> reservaSeleccionada = new MutableLiveData<>();

    public void seleccionarReserva(String hora, String barbero, String servicio, String costo) {
        reservaSeleccionada.setValue(new ReservasRequest(hora, barbero, servicio, costo));
    }

    public LiveData<ReservasRequest> getReservaSeleccionada() {
        return reservaSeleccionada;
    }
}
