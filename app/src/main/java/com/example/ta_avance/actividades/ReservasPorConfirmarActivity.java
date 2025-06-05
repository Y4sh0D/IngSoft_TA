package com.example.ta_avance.actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.reserva.ReservasRequest;
import com.example.ta_avance.viewmodel.ReservasPorConfirmarViewModel;

import java.util.List;

public class ReservasPorConfirmarActivity extends AppCompatActivity {

    private ReservasPorConfirmarViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_por_confirmar);

        viewModel = new ViewModelProvider(this).get(ReservasPorConfirmarViewModel.class);

        setupUI();
    }

    private void setupUI() {
        TableLayout tableLayout = findViewById(R.id.reservasTable);

        List<ReservasRequest> reservas = viewModel.getReservas();

        for (int i = 0; i < reservas.size(); i++) {
            ReservasRequest r = reservas.get(i);
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER);
            row.setPadding(10, 10, 10, 10);

            row.addView(createTextView(r.getHora()));
            row.addView(createTextView(r.getBarbero()));
            row.addView(createTextView(r.getServicio()));
            row.addView(createTextView("S/ " + r.getCosto()));

            // Botón Check
            ImageButton btnCheck = new ImageButton(this);
            btnCheck.setImageResource(R.drawable.baseline_check_circle_24);
            btnCheck.setBackground(null);
            btnCheck.setColorFilter(Color.parseColor("#4CAF50"));
            btnCheck.setOnClickListener(v -> {
                viewModel.confirmarReserva(r);
                Toast.makeText(this, "Confirmado", Toast.LENGTH_SHORT).show();
            });

            // Botón Cancel
            ImageButton btnCancel = new ImageButton(this);
            btnCancel.setImageResource(R.drawable.baseline_cancel_24);
            btnCancel.setBackground(null);
            btnCancel.setColorFilter(Color.parseColor("#F44336"));
            btnCancel.setOnClickListener(v -> {
                mostrarPopupMotivoCancelacion(ReservasPorConfirmarActivity.this, String.valueOf(r.getId_reserva()));
            });

            row.addView(btnCheck);
            row.addView(btnCancel);

            tableLayout.addView(row);
        }



        findViewById(R.id.volverButton).setOnClickListener(v -> finish());
    }

    private TextView createTextView(String texto) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextColor(Color.parseColor("#555555"));
        tv.setPadding(8, 8, 8, 8);
        return tv;
    }

    private void mostrarPopupMotivoCancelacion(Context context, String idReserva) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_motivo_cancelacion, null);
        builder.setView(popupView);

        EditText etMotivo = popupView.findViewById(R.id.etMotivoCancelacion);
        Button btnEnviar = popupView.findViewById(R.id.btnEnviarMotivo);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnEnviar.setOnClickListener(v -> {
            String motivo = etMotivo.getText().toString().trim();
            if (motivo.isEmpty()) {
                etMotivo.setError("Por favor ingrese un motivo");
            } else {
                dialog.dismiss();

                // Llama al ViewModel para cancelar la reserva con motivo
                // viewModel.cancelarReserva(idReserva, motivo);
                Toast.makeText(context, "Reserva cancelada con motivo: " + motivo, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
