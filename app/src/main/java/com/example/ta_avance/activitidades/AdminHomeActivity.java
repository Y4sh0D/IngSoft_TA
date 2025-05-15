package com.example.ta_avance.activitidades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.AdminHomeViewModel;

public class AdminHomeActivity extends AppCompatActivity {

    private AdminHomeViewModel viewModel;
    private TextView adminTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        viewModel = new ViewModelProvider(this).get(AdminHomeViewModel.class);

        adminTitle = findViewById(R.id.adminTitle);
        String nombre = getIntent().getStringExtra("nombre");
        String apellido = getIntent().getStringExtra("apellido");
        viewModel.setNombreYApellido(nombre, apellido);

        viewModel.nombreCompleto.observe(this, texto -> adminTitle.setText(texto));

        // Botones de navegación
        findViewById(R.id.verReservasDelDia).setOnClickListener(v ->
                startActivity(new Intent(this, ReservasActivity.class)));

        findViewById(R.id.crearUsuario).setOnClickListener(v ->
                startActivity(new Intent(this, RegistroUsuarioActivity.class)));

        findViewById(R.id.reservasPorConfirmar).setOnClickListener(v ->
                startActivity(new Intent(this, ReservasPorConfirmarActivity.class)));

        findViewById(R.id.crearServicio).setOnClickListener(v ->
                startActivity(new Intent(this, ServiciosActivity.class)));

        // Cierre de sesión
        findViewById(R.id.btnLogoutAdmin).setOnClickListener(v -> {
            viewModel.cerrarSesion();
            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
