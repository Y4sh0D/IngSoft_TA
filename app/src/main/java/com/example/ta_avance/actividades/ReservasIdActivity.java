package com.example.ta_avance.actividades;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.adapters.ReservaAdapter;
import com.example.ta_avance.dto.reserva.ReservaResponse;
import com.example.ta_avance.viewmodel.ReservasViewModel;

public class ReservasIdActivity extends AppCompatActivity {

    private ReservasViewModel viewModel;
    private RecyclerView recyclerView;
    private long usuarioId;
    private TextView tituloReservas,tvMontoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservas_id);

        usuarioId = getIntent().getLongExtra("usuarioId", -1);
        if (usuarioId == -1) {
            Toast.makeText(this, "ID de usuario inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvMontoTotal = findViewById(R.id.tvMontoTotal);
        tituloReservas = findViewById(R.id.tituloReservas);

        recyclerView = findViewById(R.id.recyclerViewReservasId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ReservasViewModel.class);
        cargarReservas();
    }

    private void cargarReservas(){
        String fecha = ""; // vacía para que el backend lo ignore
        String estado = "REALIZADA";

        viewModel.getReservas().observe(this, reservas -> {
            ReservaAdapter adapter = new ReservaAdapter(reservas, new ReservaAdapter.OnReservaClickListener() {
                @Override
                public void onVerDetallesClick(ReservaResponse reserva) {
                }

                @Override
                public void onReservaRealizadaClick(ReservaResponse reserva) {
                }
            }, estado);
            recyclerView.setAdapter(adapter);

            // Obtener montoTotal del primer elemento
            if (reservas != null && !reservas.isEmpty()) {
                long montoTotal = reservas.get(0).getMontoTotal();
                String nombreCliente = reservas.get(0).getUsuarioNombre();
                tvMontoTotal.setText("Monto Total: S/ " + montoTotal);
                tituloReservas.setText("Reservas de " + nombreCliente);
            } else {
                tvMontoTotal.setText("Monto Total: S/ 0");
                tituloReservas.setText("Reservas del cliente");
            }
        });


        viewModel.cargarReservasConId(this, fecha, estado, usuarioId);
    }
}