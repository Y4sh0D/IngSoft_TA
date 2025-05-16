package com.example.ta_avance.actividades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.content.ContextCompat;
import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.HorarioActualViewModel;

import java.util.Map;

public class HorarioActualActivity extends AppCompatActivity {
    private HorarioActualViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_actual);

        viewModel = new ViewModelProvider(this).get(HorarioActualViewModel.class);
        // Aquí conectarás con tus vistas y observarás los datos

        LinearLayout container = findViewById(R.id.containerDiasHorarioActual);

        viewModel.getHorarios().observe(this, semana -> {
            for (String dia : semana.keySet()) {
                Button diaButton = new Button(this);
                diaButton.setText(dia);
                diaButton.setBackgroundResource(R.drawable.btn_black_background);
                diaButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 24, 0, 0);
                diaButton.setLayoutParams(params);

                // Contenedor para los turnos (inicialmente oculto)
                LinearLayout turnosLayout = new LinearLayout(this);
                turnosLayout.setOrientation(LinearLayout.VERTICAL);
                turnosLayout.setVisibility(View.GONE);
                turnosLayout.setPadding(32, 8, 8, 8);

                Map<String, String> turnos = semana.get(dia);
                for (String turno : turnos.keySet()) {
                    TextView turnoView = new TextView(this);
                    turnoView.setText(turno + ": " + turnos.get(turno));
                    turnoView.setTextSize(16);
                    turnoView.setPadding(8, 4, 8, 4);
                    turnosLayout.addView(turnoView);
                }

                // Mostrar/Ocultar al hacer click
                diaButton.setOnClickListener(v -> {
                    if (turnosLayout.getVisibility() == View.VISIBLE) {
                        turnosLayout.setVisibility(View.GONE);
                    } else {
                        turnosLayout.setVisibility(View.VISIBLE);
                    }
                });

                // Agregar al contenedor
                container.addView(diaButton);
                container.addView(turnosLayout);
            }
        });
    }
}
