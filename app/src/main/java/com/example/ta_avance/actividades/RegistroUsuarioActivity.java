package com.example.ta_avance.actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.RegistroUsuarioViewModel;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private EditText usuarioInput, contraseñaInput, nombreInput, apellidoInput, correoInput, celularInput;
    private RegistroUsuarioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        usuarioInput = findViewById(R.id.usuarioInput);
        contraseñaInput = findViewById(R.id.contraseñaInput);
        nombreInput = findViewById(R.id.nombreInput);
        apellidoInput = findViewById(R.id.apellidoInput);
        correoInput = findViewById(R.id.correoInput);
        celularInput = findViewById(R.id.celularInput);
        Button registrarButton = findViewById(R.id.registrarButton);
        Button btnVolverHome = findViewById(R.id.volverButton);

        viewModel = new ViewModelProvider(this).get(RegistroUsuarioViewModel.class);

        registrarButton.setOnClickListener(v -> {
            String username = usuarioInput.getText().toString().trim();
            String password = contraseñaInput.getText().toString().trim();
            String nombre = nombreInput.getText().toString().trim();
            String apellido = apellidoInput.getText().toString().trim();
            String correo = correoInput.getText().toString().trim();
            String celular = celularInput.getText().toString().trim();

            viewModel.registrarUsuario(username, password, nombre, apellido, correo , celular);
        });

        btnVolverHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Observadores del ViewModel
        viewModel.registroExitoso.observe(this, success -> {
            if (success != null && success) {

                String celular = celularInput.getText().toString().trim();
                String username = usuarioInput.getText().toString().trim();

                String mensaje = "Hola, has sido registrado exitosamente en nuestra app.\n\n" +
                "👤 Usuario: *" + username + "*\n" +
                "🔒 Contraseña: *123456789*\n\n" +
                "📲 Descarga la app aquí y cambia tu contraseña:\n" +
                "https://pagina-barbershop.vercel.app/";

                String urlWhatsapp = "https://api.whatsapp.com/send?phone=51" + celular + "&text=" + Uri.encode(mensaje);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlWhatsapp));
                startActivity(intent);

                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.mensajeError.observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
