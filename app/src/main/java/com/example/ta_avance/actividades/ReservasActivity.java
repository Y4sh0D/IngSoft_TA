package com.example.ta_avance.actividades;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.rangos.RangoDto;
import com.example.ta_avance.viewmodel.ReservasViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReservasActivity extends AppCompatActivity {

    private ReservasViewModel viewModel;
    private LinearLayout contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        contenedor = findViewById(R.id.contenedorRangos);
        viewModel = new ViewModelProvider(this).get(ReservasViewModel.class);

        viewModel.getRangosLiveData().observe(this, rangos -> mostrarPorTipo(rangos));
        viewModel.obtenerRangos(this);
    }

    private void mostrarPorTipo(List<RangoDto> rangos){
        Map<String, List<RangoDto>> grupos = new LinkedHashMap<>();
        for(RangoDto rango : rangos) {
            grupos.computeIfAbsent(rango.getTipoHorario(),k->new ArrayList<>()).add(rango);
        }

        contenedor.removeAllViews();
        for(String tipo : grupos.keySet()){
            TextView titulo = new TextView(this);
            titulo.setText(tipo);
            titulo.setTextSize(20);
            titulo.setPadding(0,16,0,8);
            titulo.setTypeface(null, Typeface.BOLD);
            contenedor.addView(titulo);

            for (RangoDto rango : grupos.get(tipo)) {
                Button btn = new Button(this);
                btn.setText(rango.getRango());
                btn.setOnClickListener(v -> {
                        Toast.makeText(this,"Seleccionaste: " + rango.getRango(), Toast.LENGTH_SHORT).show();
                });
                contenedor.addView(btn);
            }
        }
    }
}
