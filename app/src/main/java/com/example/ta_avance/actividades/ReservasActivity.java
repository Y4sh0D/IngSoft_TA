package com.example.ta_avance.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.ReservasViewModel;

public class ReservasActivity extends AppCompatActivity {

    private ReservasViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        viewModel = new ViewModelProvider(this).get(ReservasViewModel.class);

        Button btnVolverHome = findViewById(R.id.volverButton);
        btnVolverHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    public void showBarbersAtTime(View view) {
        String timeSlot = ((Button)view).getText().toString();
        String message = viewModel.obtenerReservasPorHorario(timeSlot);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Detalles de las Reservas")
                .setMessage(message)
                .setPositiveButton("Confirmar", (dialog, which) ->
                        Toast.makeText(this, "Reserva confirmada.", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Cancelar", (dialog, which) ->
                        Toast.makeText(this, "Reserva cancelada.", Toast.LENGTH_SHORT).show());

        builder.create().show();
    }
}
