package com.example.ta_avance.actividades;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ta_avance.R;
import com.example.ta_avance.dto.reporte.DtoReporte;
import com.example.ta_avance.dto.servicio.ServicioDto;
import com.example.ta_avance.viewmodel.GestionarServicioViewModel;
import com.example.ta_avance.viewmodel.ReporteViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReporteActivity extends AppCompatActivity {

    private TextInputEditText etFechaInicio, etFechaFin;
    private Spinner spinnerServicio;
    private Button btnFiltrarReporte;
    private TextView tvServicioNombre, tvMontoTotal, tvCantidadReservas;
    private CardView cardResultado;

    private final List<ServicioDto> listaServicios = new ArrayList<>();
    private LocalDate fechaInicioSeleccionada, fechaFinSeleccionada;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte);

        etFechaInicio = findViewById(R.id.etFechaInicio);
        etFechaFin = findViewById(R.id.etFechaFin);
        spinnerServicio = findViewById(R.id.spinnerServicio);
        btnFiltrarReporte = findViewById(R.id.btnFiltrarReporte);
        tvServicioNombre = findViewById(R.id.tvServicioNombre);
        tvMontoTotal = findViewById(R.id.tvMontoTotal);
        tvCantidadReservas = findViewById(R.id.tvCantidadReservas);
        cardResultado = findViewById(R.id.cardResultado);

        etFechaInicio.setOnClickListener(v -> mostrarDatePicker(true));
        etFechaFin.setOnClickListener(v -> mostrarDatePicker(false));

        cargarServicios();

        btnFiltrarReporte.setOnClickListener(v -> filtrarReporte());
    }

    private void mostrarDatePicker(boolean esInicio) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(this, (view, y, m, d) -> {
            LocalDate fecha = LocalDate.of(y, m + 1, d);
            if (esInicio) {
                fechaInicioSeleccionada = fecha;
                etFechaInicio.setText(formatter.format(fecha));
            } else {
                fechaFinSeleccionada = fecha;
                etFechaFin.setText(formatter.format(fecha));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    private void cargarServicios(){
        GestionarServicioViewModel servicioViewModel = new GestionarServicioViewModel();
        servicioViewModel.obtenerServicios(this, new GestionarServicioViewModel.ServicioCallback() {
            @Override
            public void onSuccess(List<ServicioDto> servicios) {
                listaServicios.clear();
                listaServicios.addAll(servicios);

                List<String> nombres = new ArrayList<>();
                nombres.add("Todos");
                for (ServicioDto s : servicios) nombres.add(s.getNombre());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(ReporteActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, nombres);
                spinnerServicio.setAdapter(adapter);
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(ReporteActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filtrarReporte(){
        if (fechaInicioSeleccionada == null || fechaFinSeleccionada == null) {
            Toast.makeText(this, "Selecciona ambas fechas", Toast.LENGTH_SHORT).show();
            return;
        }

        String servicioSeleccionado;
        int pos = spinnerServicio.getSelectedItemPosition();
        if (pos == 0) {            // "Todos"
            servicioSeleccionado = null;
        } else {
            servicioSeleccionado = listaServicios.get(pos - 1).getNombre();
        }

        cardResultado.setVisibility(View.GONE);

        ReporteViewModel reporteViewModel = new ReporteViewModel();
        reporteViewModel.obtenerReporte(this, fechaInicioSeleccionada, fechaFinSeleccionada,
                servicioSeleccionado, new ReporteViewModel.ReporteCallback() {
            @Override
            public void onSuccess(DtoReporte reporte) {
                tvServicioNombre.setText((reporte.getServicioNombre() != null ? reporte.getServicioNombre() : "Todos"));
                tvMontoTotal.setText("S/ " + reporte.getMontoTotal());
                tvCantidadReservas.setText("" + reporte.getCantidadReservas());

                cardResultado.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(ReporteActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }
}