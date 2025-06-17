package com.example.ta_avance.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.adapters.UsuarioAdapter;
import com.example.ta_avance.dto.login.LoginRequest;

import com.example.ta_avance.R;
import com.example.ta_avance.viewmodel.ListarUsuarioViewModel;

import java.util.List;

public class ListarUsuarioActivity extends AppCompatActivity {

    private ListarUsuarioViewModel viewModel;
    private RecyclerView recyclerView;
    private Button btnAgregarUsuario;
    private List<LoginRequest> listaUsuarios;
    private UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuario);

        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);
        btnAgregarUsuario.setOnClickListener(v -> startActivity(new Intent(this, RegistroUsuarioActivity.class)));

        viewModel = new ListarUsuarioViewModel();
        cargarLista();
    }

    private void cargarLista(){
        viewModel.obtenerUsuarios(this, new ListarUsuarioViewModel.UsuarioCallback() {
            @Override
            public void onSuccess(List<LoginRequest> usuarios) {
                listaUsuarios = usuarios;
                adapter = new UsuarioAdapter(usuarios, usuario -> enviarWsp(usuario));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(ListarUsuarioActivity.this,mensaje,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarWsp(LoginRequest usuario){
        String numero = usuario.getCelular();
        String mensaje = "Hola *" + usuario.getNombre() + "*, tu cuenta ha sido creada. Aquí tienes tus credenciales:\n\n" +
                "👤 Usuario: *" + usuario.getUsername() + "*\n" +
                "🔑 Contraseña: *123456789*\n\n" +
                "📲 Descarga la app desde aquí: https://pagina-barbershop.vercel.app/\n" +
                "Por favor, cambia tu contraseña después de ingresar.";

        try {
            String uri = "https://wa.me/51" + numero + "?text=" + java.net.URLEncoder.encode(mensaje, "UTF-8");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse(uri));
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }
}
