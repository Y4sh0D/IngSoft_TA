package com.example.ta_avance.activitidades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.ServiciosViewModel;

public class ServiciosActivity extends AppCompatActivity {

    private EditText nombreInput, precioInput;
    private Button registrarButton;
    private ServiciosViewModel serviciosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        nombreInput = findViewById(R.id.servicioInput);
        precioInput = findViewById(R.id.precioInput);
        registrarButton = findViewById(R.id.registrarButton);
        Button btnVolverHome = findViewById(R.id.volverButton);

        serviciosViewModel = new ViewModelProvider(this).get(ServiciosViewModel.class);

        registrarButton.setOnClickListener(v -> {
            String nombre = nombreInput.getText().toString().trim();
            String precio = precioInput.getText().toString().trim();
            serviciosViewModel.registrarServicio(nombre, precio);
        });

        serviciosViewModel.getMensaje().observe(this, mensaje -> {
            if (mensaje != null && !mensaje.isEmpty()) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                if (mensaje.contains("exitosamente")) {
                    finish(); // cerrar solo si fue exitoso
                }
            }
        });

        btnVolverHome.setOnClickListener(v -> {
            Intent intent = new Intent(ServiciosActivity.this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
