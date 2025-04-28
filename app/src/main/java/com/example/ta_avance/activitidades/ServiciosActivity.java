package com.example.ta_avance.activitidades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ta_avance.R;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.modelo.ServiciosRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiciosActivity extends AppCompatActivity {

    private EditText nombreInput, precioInput;
    private Button registrarButton;
    private AuthApiService authApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        // Enlazar elementos del layout
        nombreInput = findViewById(R.id.servicioInput);  // Cambié el nombre del EditText
        precioInput = findViewById(R.id.precioInput);
        registrarButton = findViewById(R.id.registrarButton);

        // Crear Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/") // Asegúrate de usar la base URL correcta
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApiService = retrofit.create(AuthApiService.class);

        // Configurar botón para registrar el servicio
        registrarButton.setOnClickListener(v -> registrarServicio());
    }

    private void registrarServicio() {
        String nombre = nombreInput.getText().toString().trim();
        String precioString = precioInput.getText().toString().trim();

        if (nombre.isEmpty() || precioString.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int precio = Integer.parseInt(precioString); // Asumiendo que el precio es un entero
            ServiciosRequest request = new ServiciosRequest(nombre, precio);

            Call<Void> call = authApiService.crear(request);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ServiciosActivity.this, "Servicio registrado exitosamente", Toast.LENGTH_SHORT).show();
                        finish(); // cerrar esta pantalla
                    } else {
                        Toast.makeText(ServiciosActivity.this, "Error al registrar servicio: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ServiciosActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingresa un precio válido", Toast.LENGTH_SHORT).show();
        }
    }

}
