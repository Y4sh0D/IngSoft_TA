package com.example.ta_avance.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.adapters.ValoracionAdapter;
import com.example.ta_avance.dto.valoracion.ValoracionDto;
import com.example.ta_avance.viewmodel.ListarValoracionViewModel;

import java.util.List;

public class ListarValoracionActivity extends AppCompatActivity {

    private ListarValoracionViewModel viewModel;
    private RecyclerView recyclerView;
    private List<ValoracionDto> listarValoraciones;
    private ValoracionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_valoracion);

        recyclerView =findViewById(R.id.recyclerViewValoraciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ListarValoracionViewModel();
        cargarLista();
    }

    private void cargarLista(){
        viewModel.obtenerValoraciones(this, new ListarValoracionViewModel.ValoracionCallback() {
            @Override
            public void onSuccess(List<ValoracionDto> valoraciones) {
                listarValoraciones = valoraciones;
                adapter = new ValoracionAdapter(valoraciones,valoracion -> enviarWsp(valoracion));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(ListarValoracionActivity.this,mensaje,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void enviarWsp(ValoracionDto valoracion){
        String numero = valoracion.getCelular();
        String mensaje = "Hola *" + valoracion.getUsuario_nombre() + "*, muchas gracias por tu opinión.\n"+
                "Nos ayuda a mejorar nuestro servicio.";
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