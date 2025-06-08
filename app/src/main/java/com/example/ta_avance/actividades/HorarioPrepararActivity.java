package com.example.ta_avance.actividades;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.barbero.BarberoDto;
import com.example.ta_avance.viewmodel.HorarioPrepararViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HorarioPrepararActivity extends AppCompatActivity {

    private HorarioPrepararViewModel viewModel;
    private LinearLayout containerDias;
    private static final Map<String, String> turnoIds = new HashMap<>();
    static {
        turnoIds.put("MAÑANA", "1");
        turnoIds.put("TARDE", "2");
        turnoIds.put("NOCHE", "3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_preparar);

        containerDias = findViewById(R.id.containerDias);
        viewModel = new ViewModelProvider(this).get(HorarioPrepararViewModel.class);

        viewModel.getDias().observe(this, dias -> {
            containerDias.removeAllViews();
            for (String dia : dias) {
                CardView card = new CardView(this);
                card.setRadius(16);
                card.setCardElevation(6);
                card.setUseCompatPadding(true);
                card.setContentPadding(16, 16, 16, 16);

                Button btnDia = new Button(this);
                btnDia.setText(dia);
                btnDia.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_700));
                btnDia.setTextColor(Color.WHITE);
                btnDia.setAllCaps(false);
                btnDia.setTextSize(16);
                btnDia.setOnClickListener(v -> mostrarPopupDia(dia));

                card.addView(btnDia);
                containerDias.addView(card);

            }
        });
        Button btnConfirmar = findViewById(R.id.btnConfirmarHorario);
        btnConfirmar.setOnClickListener(v -> {
            viewModel.confirmarHorario(this);
        });
        Button btnExportarHorario = findViewById(R.id.btnExportarHorario);
        btnExportarHorario.setOnClickListener(v -> {
            viewModel.exportarHorario(this);
        });


        viewModel.cargarBarberos(this);
    }

    private void mostrarPopupDia(String dia) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_preparar_dia, null);

        TextView tituloDia = popupView.findViewById(R.id.tituloDia);
        LinearLayout contenedorTurnos = popupView.findViewById(R.id.contenedorTurnos);
        Button btnGuardar = popupView.findViewById(R.id.btnGuardar);

        tituloDia.setText("Preparación " + dia);

        contenedorTurnos.removeAllViews();

        List<String> turnos = viewModel.getTurnos().getValue();
        List<BarberoDto> barberos = viewModel.getBarberos().getValue();

        if (turnos == null || barberos == null) {
            Toast.makeText(this, "Error cargando turnos o barberos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Por cada turno (ej: "Mañana", "Tarde"), crear sección con checkboxes de barberos
        Map<String, LinearLayout> layoutsPorTurno = new HashMap<>();

        for (String turno : turnos) {
            TextView tvTurno = new TextView(this);
            tvTurno.setText(turno);
            tvTurno.setTextSize(18);
            tvTurno.setTypeface(null, Typeface.BOLD);
            tvTurno.setPadding(0, 20, 0, 10);
            contenedorTurnos.addView(tvTurno);

            LinearLayout layoutBarberos = new LinearLayout(this);
            layoutBarberos.setOrientation(LinearLayout.VERTICAL);

            for (BarberoDto barbero : barberos) {
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(barbero.getNombre());
                checkBox.setTag(barbero.getBarbero_id());
                layoutBarberos.addView(checkBox);
            }
            contenedorTurnos.addView(layoutBarberos);
            layoutsPorTurno.put(turno, layoutBarberos);
        }

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

        btnGuardar.setOnClickListener(v -> {
            Map<Long, List<Long>> turnosPorTipo = new HashMap<>();

            for (String turno : turnos) {
                LinearLayout layout = layoutsPorTurno.get(turno);
                if (layout == null) continue;

                List<Long> barberoIdsSeleccionados = layout.getTouchables().stream()
                        .filter(view -> view instanceof CheckBox)
                        .map(view -> (CheckBox) view)
                        .filter(CheckBox::isChecked)
                        .map(cb -> ((Integer) cb.getTag()).longValue())  // Convertir Integer a Long
                        .collect(Collectors.toList());

                String turnoIdStr = turnoIds.get(turno.toUpperCase());
                if (turnoIdStr != null) {
                    Long turnoId = Long.parseLong(turnoIdStr);   // Convertir String a Long
                    turnosPorTipo.put(turnoId, barberoIdsSeleccionados);
                }
            }

            viewModel.guardarTurnosDia(this, dia, turnosPorTipo);
            popupWindow.dismiss();
        });
    }


}