package com.example.ta_avance.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ta_avance.dto.reserva.ReservasRequest;

import java.util.ArrayList;
import java.util.List;

public class ReservasPorConfirmarViewModel extends ViewModel {

    private final MutableLiveData<List<ReservasRequest>> reservasLiveData = new MutableLiveData<>();
    private final List<ReservasRequest> reservasSimuladas = new ArrayList<>();

    public ReservasPorConfirmarViewModel() {
        cargarDatosSimulados();
    }

    private void cargarDatosSimulados() {
        reservasSimuladas.add(new ReservasRequest(1,"10:00 - 10:30", "Carlos", "Corte de cabello", 30));
        reservasSimuladas.add(new ReservasRequest(2,"11:00 - 11:30", "Laura", "Afeitado", 20));
        reservasLiveData.setValue(reservasSimuladas);
    }

    public List<ReservasRequest> getReservas() {
        return reservasLiveData.getValue();
    }

    public void confirmarReserva(ReservasRequest reserva) {
        // Lógica simulada
        Log.d("ReservasVM", "Reserva confirmada: " + reserva.getBarbero());
    }

    public void cancelarReserva(ReservasRequest reserva) {
        // Lógica simulada
        Log.d("ReservasVM", "Reserva cancelada: " + reserva.getBarbero());
    }
}

