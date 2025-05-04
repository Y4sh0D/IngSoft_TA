package com.example.ta_avance.activitidades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ta_avance.R;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.PhantomReference;

public class  AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhome);  // Asegúrate de tener el layout adminhome.xml

        String nombre = getIntent().getStringExtra("nombre");
        String apellido = getIntent().getStringExtra("apellido");

        TextView adminTitle = findViewById(R.id.adminTitle);
        adminTitle.setText("Hola " + nombre + " " + apellido);

        // Botón "Ver Reservas del Día"
        Button verReservasDelDiaButton = findViewById(R.id.verReservasDelDia);
        verReservasDelDiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a la actividad de Reservas
                Intent intent = new Intent(AdminHomeActivity.this, ReservasActivity.class);
                startActivity(intent);
            }
        });

        // Botón "Crear Usuario/Cliente"
        Button crearUsuarioButton = findViewById(R.id.crearUsuario);
        crearUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a la actividad de Registro de Usuario
                Intent intent = new Intent(AdminHomeActivity.this, RegistroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        // Botón "Reservas por Confirmar"
        Button reservasPorConfirmarButton = findViewById(R.id.reservasPorConfirmar);
        reservasPorConfirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a la actividad de Reservas por Confirmar
                Intent intent = new Intent(AdminHomeActivity.this, ReservasPorConfirmarActivity.class);
                startActivity(intent);
            }
        });

        // Botón "Gestión de servicios"
        Button crearServicioButton = findViewById(R.id.crearServicio);
        crearServicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a la actividad de Gestionar Reservas
                Intent intent = new Intent(AdminHomeActivity.this, ServiciosActivity.class);
                startActivity(intent);
            }
        });

        Button btnLogoutAdmin = findViewById(R.id.btnLogoutAdmin);
        btnLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v){
                SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                prefs.edit().remove("token").apply();

                Toast.makeText(AdminHomeActivity.this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
