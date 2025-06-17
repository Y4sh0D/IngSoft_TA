package com.example.ta_avance.actividades;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.adapters.ReservaAdapter;
import com.example.ta_avance.viewmodel.ReservasViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Locale;

public class ReservasRealizadasActivity extends AppCompatActivity {

    private ReservasViewModel viewModel;
    private RecyclerView recyclerView;
    EditText etFechaSeleccionada;
    private TextView tituloReservasPorConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        recyclerView = findViewById(R.id.recyclerViewReservasPorConfirmar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Locale nuevoLocale = new Locale("es", "ES");
        Locale.setDefault(nuevoLocale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(nuevoLocale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        viewModel = new ViewModelProvider(this).get(ReservasViewModel.class);
        tituloReservasPorConfirmar = findViewById(R.id.tituloReservasPorConfirmar);
        viewModel.setNuevoTitulo2();
        viewModel.NuevoTitulo.observe(this,texto -> tituloReservasPorConfirmar.setText(texto));

        viewModel.getReservas().observe(this, reservas -> {
            ReservaAdapter adapter = new ReservaAdapter(reservas, reserva -> {},false);
            recyclerView.setAdapter(adapter);
        });

        String fechaActual = LocalDate.now().toString();
        viewModel.cargarReservas(this, fechaActual, "REALIZADA");


        etFechaSeleccionada = findViewById(R.id.etFechaSeleccionada);
        etFechaSeleccionada.setOnClickListener(v -> mostrarDatePicker());
    }

    private void mostrarDatePicker() {
        LocalDate hoy = LocalDate.now();

        // La fecha máxima es hoy (no se puede seleccionar el futuro)
        long fechaMax = hoy.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // Puedes ponerle un mínimo opcional (por ejemplo, 50 años atrás), o dejarlo abierto
        LocalDate fechaMinima = hoy.minusYears(50); // Ajusta si lo deseas
        long fechaMin = fechaMinima.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setStart(fechaMin)
                .setEnd(fechaMax)
                .setValidator(
                        CompositeDateValidator.allOf(
                                Arrays.asList(
                                        DateValidatorPointBackward.before(fechaMax)
                                )
                        )
                );

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .setSelection(fechaMax) // Por defecto selecciona hoy
                .setCalendarConstraints(constraintsBuilder.build());

        MaterialDatePicker<Long> datePicker = builder.build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            LocalDate fechaSeleccionada = Instant.ofEpochMilli(selection)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate();

            etFechaSeleccionada.setText(fechaSeleccionada.toString());
            viewModel.cargarReservas(this, fechaSeleccionada.toString(), "REALIZADA");
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }


}
