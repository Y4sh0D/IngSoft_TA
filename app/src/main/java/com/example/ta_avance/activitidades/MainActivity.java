package com.example.ta_avance.activitidades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ta_avance.R;
import com.example.ta_avance.api.AuthApiService;
import com.example.ta_avance.modelo.LoginRequest;
import com.example.ta_avance.modelo.LoginResponse;

import com.auth0.android.jwt.JWT;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText campoUsuario, campoContraseña;
    private Button btnIngresarApp;
    private AuthApiService authApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Tu layout se llama activity_main.xml

        campoUsuario = findViewById(R.id.campoUsuario);
        campoContraseña = findViewById(R.id.campoContraseña);
        btnIngresarApp = findViewById(R.id.btnIngresarApp);

        // Crear Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/") // Aquí sigue siendo tu base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApiService = retrofit.create(AuthApiService.class);

        // Acciones al presionar el botón
        btnIngresarApp.setOnClickListener(v -> {
            String usuario = campoUsuario.getText().toString();
            String contraseña = campoContraseña.getText().toString();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            login(usuario, contraseña);
        });
    }

    private void login(String usuario, String contraseña) {
        LoginRequest request = new LoginRequest(usuario, contraseña);
        Call<LoginResponse> call = authApiService.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getData().getToken();

                    // Decodificamos el JWT
                    JWT jwt = new JWT(token);

                    String nombre = jwt.getClaim("nombre").asString();
                    String apellido = jwt.getClaim("apellido").asString();

                    // Extraemos el rol del token
                    String role = jwt.getClaim("role").asString();

                    // Verificar si el rol es ADMIN
                    if ("ADMIN".equals(role)) {
                        Toast.makeText(MainActivity.this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                        // Agregamos el nombre y apellido al Intent
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("apellido", apellido);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Rol no autorizado para esta aplicación", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error al iniciar sesión: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
