package com.example.ta_avance.actividades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.HorarioActualViewModel;

import java.util.Map;

public class HorarioActualActivity extends AppCompatActivity {
    private HorarioActualViewModel viewModel;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_actual);

        container = findViewById(R.id.containerDiasHorarioActual);
        viewModel = new ViewModelProvider(this).get(HorarioActualViewModel.class);

        // 🔁 Observar LiveData
        viewModel.getHorarios().observe(this, semana -> {
            container.removeAllViews(); // 🧹 Limpiar para evitar duplicados

            // Orden fijo de días y turnos
            String[] ordenDias = {"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};
            String[] ordenTurnos = {"MAÑANA", "TARDE", "NOCHE"};

            for (String dia : ordenDias) {
                if (semana.containsKey(dia)) {
                    Button diaButton = new Button(this);
                    diaButton.setText(dia);
                    diaButton.setBackgroundResource(R.drawable.btn_black_background);
                    diaButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 24, 0, 0);
                    diaButton.setLayoutParams(params);

                    // Contenedor de turnos
                    LinearLayout turnosLayout = new LinearLayout(this);
                    turnosLayout.setOrientation(LinearLayout.VERTICAL);
                    turnosLayout.setVisibility(View.GONE);
                    turnosLayout.setPadding(32, 8, 8, 8);

                    Map<String, String> turnos = semana.get(dia);
                    for (String turno : ordenTurnos) {
                        if (turnos.containsKey(turno)) {
                            TextView turnoView = new TextView(this);
                            turnoView.setText(turno + ": " + turnos.get(turno));
                            turnoView.setTextSize(16);
                            turnoView.setPadding(8, 4, 8, 4);
                            turnosLayout.addView(turnoView);
                        }
                    }

                    diaButton.setOnClickListener(v -> {
                        turnosLayout.setVisibility(
                                turnosLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
                        );
                    });

                    container.addView(diaButton);
                    container.addView(turnosLayout);
                }
            }

        });

        // 🚀 Llamada real a la API
        viewModel.cargarHorarios(this);
    }
}
