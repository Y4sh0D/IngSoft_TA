package com.example.ta_avance.activitidades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ta_avance.R;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.api.AuthInterceptor;
import com.example.ta_avance.modelo.LoginRequest;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private EditText usuarioInput, contraseñaInput, nombreInput, apellidoInput;
    private Button registrarButton;
    private AuthApiService authApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        usuarioInput = findViewById(R.id.usuarioInput);
        contraseñaInput = findViewById(R.id.contraseñaInput);
        nombreInput = findViewById(R.id.nombreInput);
        apellidoInput = findViewById(R.id.apellidoInput);
        registrarButton = findViewById(R.id.registrarButton);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(this))
                .build();

        // Crear Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/") // misma base URL que login
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApiService = retrofit.create(AuthApiService.class);

        registrarButton.setOnClickListener(v -> registrarUsuario());

        Button btnVolverHome = findViewById(R.id.volverButton);
        btnVolverHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver a AdminHomeActivity y cerrar la actual
                Intent intent = new Intent(RegistroUsuarioActivity.this, AdminHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registrarUsuario() {
        String username = usuarioInput.getText().toString().trim();
        String password = contraseñaInput.getText().toString().trim();
        String nombre = nombreInput.getText().toString().trim();
        String apellido = apellidoInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto LoginRequest para el registro
        LoginRequest request = new LoginRequest(username, password,nombre,apellido);
        request.setUsername(username);
        request.setPassword(password);
        request.setNombre(nombre);
        request.setApellido(apellido);

        Call<Void> call = authApiService.register(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroUsuarioActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // cerrar esta pantalla
                } else {
                    Toast.makeText(RegistroUsuarioActivity.this, "Error al registrar usuario: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegistroUsuarioActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
