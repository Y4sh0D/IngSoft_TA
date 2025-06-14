package com.example.ta_avance.actividades;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ta_avance.R;
import com.example.ta_avance.adapters.ReservaAdapter;
import com.example.ta_avance.dto.reserva.ReservaResponse;
import com.example.ta_avance.viewmodel.ReservasViewModel;
import com.google.android.material.button.MaterialButton;
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

public class ReservasPorConfirmarActivity extends AppCompatActivity {

    private ReservasViewModel viewModel;
    private RecyclerView recyclerView;
    EditText etFechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        viewModel.getReservas().observe(this, reservas -> {
            ReservaAdapter adapter = new ReservaAdapter(reservas, this::mostrarPopupDetalle,true);
            recyclerView.setAdapter(adapter);
        });

        viewModel.cambioEstadoExitoso.observe(this, exitoso -> {
            if (Boolean.TRUE.equals(exitoso)) {
                Toast.makeText(this, "Estado actualizado", Toast.LENGTH_SHORT).show();
                recargarReservasActuales();
            }
        });

        viewModel.mensajeError.observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

        String fechaActual = LocalDate.now().toString();
        viewModel.cargarReservas(this, fechaActual, "CREADA");

        etFechaSeleccionada = findViewById(R.id.etFechaSeleccionada);
        etFechaSeleccionada.setOnClickListener(v -> mostrarDatePicker());
    }

    private void mostrarPopupDetalle(ReservaResponse reserva) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_reserva_detalle, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(popupView).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        TextView tvUsuario = popupView.findViewById(R.id.tvDetalleUsuario);
        TextView tvBarbero = popupView.findViewById(R.id.tvDetalleBarbero);
        TextView tvHorario = popupView.findViewById(R.id.tvDetalleHorario);
        TextView tvServicio = popupView.findViewById(R.id.tvDetalleServicio);
        TextView tvFechaReserva = popupView.findViewById(R.id.tvDetalleFechaReserva);
        TextView tvFechaCreacion = popupView.findViewById(R.id.tvDetalleFechaCreacion);
        TextView tvPrecio = popupView.findViewById(R.id.tvDetallePrecio);
        TextView tvAdicionales = popupView.findViewById(R.id.tvDetalleAdicionales);
        ImageView ivComprobante = popupView.findViewById(R.id.ivComprobante);
        MaterialButton btnConfirmar = popupView.findViewById(R.id.btnConfirmarReserva);
        MaterialButton btnCancelar = popupView.findViewById(R.id.btnCancelarReserva);

        tvUsuario.setText("Usuario: " + reserva.getUsuarioNombre());
        tvBarbero.setText("Barbero: " + reserva.getBarberoNombre());
        tvHorario.setText("Horario: " + reserva.getHorarioRango());
        tvServicio.setText("Servicio: " + reserva.getServicioNombre());
        tvFechaReserva.setText("Fecha reserva: " + reserva.getFechaReserva());
        tvFechaCreacion.setText("Creado el: " + reserva.getFechaCreacion());
        tvPrecio.setText("Precio: s/" + reserva.getPrecioServicio());
        tvAdicionales.setText("Adicionales: " + reserva.getAdicionales());

        if (reserva.getUrlPago() != null && !reserva.getUrlPago().isEmpty()) {
            Glide.with(this).load(reserva.getUrlPago()).into(ivComprobante);
        } else {
            ivComprobante.setVisibility(View.GONE);
        }

        btnConfirmar.setOnClickListener(v -> {
            dialog.dismiss();
            mostrarPopupMotivo(reserva.getReservaId(), "CONFIRMADA");
        });

        btnCancelar.setOnClickListener(v -> {
            dialog.dismiss();
            mostrarPopupMotivo(reserva.getReservaId(), "CANCELADA");
        });

        dialog.show();
    }

    private void mostrarPopupMotivo(Long reservaId, String nuevoEstado) {
        View motivoView = LayoutInflater.from(this).inflate(R.layout.popup_motivo_cancelacion, null);
        AlertDialog motivoDialog = new AlertDialog.Builder(this).setView(motivoView).create();

        EditText etMotivoCancelacion = motivoView.findViewById(R.id.etMotivoCancelacion);
        MaterialButton btnEnviarMotivo = motivoView.findViewById(R.id.btnEnviarMotivo);

        btnEnviarMotivo.setOnClickListener(v -> {
            String motivo = etMotivoCancelacion.getText().toString().trim();
            viewModel.cambiarEstadoReserva(this, reservaId, nuevoEstado, motivo);
            motivoDialog.dismiss();
        });

        motivoDialog.show();
    }

    private void mostrarDatePicker() {
        LocalDate hoy = LocalDate.now();

        LocalDate inicioSemana = hoy.with(DayOfWeek.MONDAY);
        LocalDate finSemana = hoy.with(DayOfWeek.SUNDAY);

        long fechaMin = hoy.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long fechaMax = finSemana.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setStart(fechaMin)
                .setEnd(fechaMax)
                .setValidator(
                        CompositeDateValidator.allOf(
                                Arrays.asList(
                                        DateValidatorPointForward.from(fechaMin),
                                        DateValidatorPointBackward.before(fechaMax)
                                )
                        )
                );

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .setSelection(fechaMin)
                .setCalendarConstraints(constraintsBuilder.build());

        MaterialDatePicker<Long> datePicker = builder.build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            LocalDate fechaSeleccionada = Instant.ofEpochMilli(selection)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            etFechaSeleccionada.setText(fechaSeleccionada.toString());
            viewModel.cargarReservas(this, fechaSeleccionada.toString(), "CREADA");
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void recargarReservasActuales() {
        String fecha = etFechaSeleccionada.getText().toString().isEmpty()
                ? LocalDate.now().toString()
                : etFechaSeleccionada.getText().toString();

        viewModel.cargarReservas(this, fecha, "CREADA");
    }
}
