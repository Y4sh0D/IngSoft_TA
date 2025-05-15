package com.example.ta_avance.activitidades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ta_avance.R;

import androidx.lifecycle.ViewModelProvider;
import com.example.ta_avance.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private EditText campoUsuario, campoContraseña;
    private Button btnIngresarApp, btnOlvideContrasena;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoUsuario = findViewById(R.id.campoUsuario);
        campoContraseña = findViewById(R.id.campoContraseña);
        btnIngresarApp = findViewById(R.id.btnIngresarApp);
        btnOlvideContrasena = findViewById(R.id.btnOlvideContrasena);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        btnIngresarApp.setOnClickListener(v -> {
            String usuario = campoUsuario.getText().toString();
            String contraseña = campoContraseña.getText().toString();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                if (usuario.isEmpty()) campoUsuario.startAnimation(shakeAnimation);
                if (contraseña.isEmpty()) campoContraseña.startAnimation(shakeAnimation);
            } else {
                mainViewModel.login(usuario, contraseña);
            }
        });

        btnOlvideContrasena.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecuperarContraActivity.class);
            startActivity(intent);
        });

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Anima el logo, los campos y botones
        findViewById(R.id.diamondLogo).startAnimation(fadeIn);
        findViewById(R.id.layoutUsuario).startAnimation(fadeIn); // si usas TextInputLayout
        findViewById(R.id.layoutContraseña).startAnimation(fadeIn);
        btnIngresarApp.startAnimation(fadeIn);
        btnOlvideContrasena.startAnimation(fadeIn);

        mainViewModel.loginStatus.observe(this, status -> {
            switch (status) {
                case "SUCCESS":
                    Intent intent = new Intent(this, AdminHomeActivity.class);
                    intent.putExtra("nombre", mainViewModel.nombre.getValue());
                    intent.putExtra("apellido", mainViewModel.apellido.getValue());
                    startActivity(intent);
                    finish();
                    break;
                case "NO_ADMIN":
                    Toast.makeText(this, "Rol no autorizado", Toast.LENGTH_SHORT).show();
                    break;
                case "INVALID":
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "Error: " + status, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
