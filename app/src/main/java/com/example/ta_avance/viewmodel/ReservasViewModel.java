package com.example.ta_avance.viewmodel;

import androidx.lifecycle.ViewModel;

public class ReservasViewModel extends ViewModel {

    public String obtenerReservasPorHorario(String timeSlot) {
        switch (timeSlot) {
            case "9:00 - 9:30":
                return "Reserva 1\nBarbera: Diego\nCliente: Cliente X\nServicio: Corte de cabello\nCosto: S/ 30\n\n" +
                        "Reserva 2\nBarbera: Laura\nCliente: Cliente Y\nServicio: Afeitado\nCosto: S/ 20";
            case "9:30 - 10:00":
                return "Reserva 1\nBarbera: Diego\nCliente: Cliente Z\nServicio: Afeitado\nCosto: S/ 20\n\n" +
                        "Reserva 2\nBarbera: Carlos\nCliente: Cliente W\nServicio: Corte de cabello\nCosto: S/ 30";
            default:
                return "No hay reservas en este horario.";
        }
    }
}
