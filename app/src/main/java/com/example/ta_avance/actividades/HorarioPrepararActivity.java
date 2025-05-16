package com.example.ta_avance.actividades;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.HorarioPrepararViewModel;

import java.util.List;

public class HorarioPrepararActivity extends AppCompatActivity {

    private HorarioPrepararViewModel viewModel;
    private LinearLayout containerDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_preparar);

        viewModel = new ViewModelProvider(this).get(HorarioPrepararViewModel.class);
        containerDias = findViewById(R.id.containerDias);

        viewModel.getDias().observe(this, dias -> {
            for (String dia : dias) {
                Button btnDia = new Button(this);
                btnDia.setText(dia);
                btnDia.setBackgroundResource(R.drawable.selector_boton); // Usa tu selector personalizado
                btnDia.setPadding(16, 16, 16, 16);
                btnDia.setTextSize(16);
                btnDia.setAllCaps(false);

                btnDia.setOnClickListener(v -> mostrarPopupDia(dia));
                containerDias.addView(btnDia);
            }
        });
    }

    private void mostrarPopupDia(String dia) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_preparar_dia, null);
        TextView tituloDia = popupView.findViewById(R.id.tituloDia);
        LinearLayout contenedorTurnos = popupView.findViewById(R.id.contenedorTurnos);
        Button btnGuardar = popupView.findViewById(R.id.btnGuardar);

        tituloDia.setText("Preparación " + dia);

        viewModel.getTurnos().observe(this, turnos -> {
            viewModel.getBarberos().observe(this, barberos -> {
                contenedorTurnos.removeAllViews();
                for (String turno : turnos) {
                    TextView label = new TextView(this);
                    label.setText(turno + ":");
                    label.setTextSize(16);
                    label.setPadding(0, 12, 0, 4);
                    contenedorTurnos.addView(label);

                    RadioGroup grupo = new RadioGroup(this);
                    for (String barbero : barberos) {
                        RadioButton radio = new RadioButton(this);
                        radio.setText(barbero);
                        grupo.addView(radio);
                    }
                    contenedorTurnos.addView(grupo);
                }
            });
        });

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

        btnGuardar.setOnClickListener(v -> {
            popupWindow.dismiss();
            Toast.makeText(this, "Guardado para " + dia, Toast.LENGTH_SHORT).show();
            // Aquí luego podrás capturar selecciones y enviarlas al backend
        });
    }
}
