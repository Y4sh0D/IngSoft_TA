package com.example.ta_avance.activitidades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.ReservasPorConfirmarViewModel;

public class ReservasPorConfirmarActivity extends AppCompatActivity {

    private ReservasPorConfirmarViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_por_confirmar);

        viewModel = new ViewModelProvider(this).get(ReservasPorConfirmarViewModel.class);

        findViewById(R.id.reservaRow1).setOnClickListener(v ->
                viewModel.seleccionarReserva("10:00 - 10:30", "Carlos", "Corte de cabello", "S/ 30")
        );

        findViewById(R.id.reservaRow2).setOnClickListener(v ->
                viewModel.seleccionarReserva("11:00 - 11:30", "Laura", "Afeitado", "S/ 20")
        );

        Button btnVolverHome = findViewById(R.id.volverButton);
        btnVolverHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        viewModel.getReservaSeleccionada().observe(this, reserva -> {
            if (reserva != null) {
                mostrarDetallesReserva(reserva.getHora(), reserva.getBarbero(), reserva.getServicio(), reserva.getCosto());
            }
        });
    }

    private void mostrarDetallesReserva(String hora, String barbero, String servicio, String costo) {
        new AlertDialog.Builder(this)
                .setTitle("Detalles de la Reserva")
                .setMessage("Fecha y Hora: " + hora + "\n" +
                        "Barbera: " + barbero + "\n" +
                        "Servicio: " + servicio + "\n" +
                        "Costo: " + costo)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    // Lógica para confirmar la reserva (si la implementas después)
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Lógica para cancelar la reserva (si la implementas después)
                })
                .create()
                .show();
    }
}
