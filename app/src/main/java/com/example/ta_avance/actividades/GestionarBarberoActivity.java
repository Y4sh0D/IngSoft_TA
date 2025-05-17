package com.example.ta_avance.actividades;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_avance.R;
import com.example.ta_avance.adapters.BarberoAdapter;
import com.example.ta_avance.dto.barbero.BarberoDto;
import com.example.ta_avance.viewmodel.GestionarBarberoViewModel;

import java.util.List;


public class GestionarBarberoActivity extends AppCompatActivity {
    // Dentro de la clase:
    private GestionarBarberoViewModel viewModel;
    private RecyclerView recyclerView;
    private Button btnAgregarBarbero;
    private List<BarberoDto> listaBarberos;
    private BarberoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_barbero);

        recyclerView = findViewById(R.id.recyclerViewBarberos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAgregarBarbero = findViewById(R.id.btnAgregarBarbero);
        btnAgregarBarbero.setOnClickListener(v -> mostrarPopupNuevoBarbero(v));

        viewModel = new GestionarBarberoViewModel();
        cargarLista();
    }

    private void cargarLista() {
        viewModel.obtenerBarberos(this, new GestionarBarberoViewModel.BarberoCallback() {
            @Override
            public void onSuccess(List<BarberoDto> barberos) {
                listaBarberos = barberos;
                adapter = new BarberoAdapter(barberos, new BarberoAdapter.OnBarberoClickListener() {
                    @Override
                    public void onActualizar(BarberoDto barbero) {
                        mostrarPopupActualizarBarbero(barbero);
                    }

                    @Override
                    public void onEliminar(BarberoDto barbero) {
                        eliminarBarbero(barbero.getBarbero_id());
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(GestionarBarberoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarPopupActualizarBarbero(BarberoDto barbero) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_nuevo_barbero, null); // Reutilizamos el mismo layout

        EditText etNombre = popupView.findViewById(R.id.etNombreNuevoBarbero);
        Button btnCrear = popupView.findViewById(R.id.btnCrearBarbero);
        Button btnCancelar = popupView.findViewById(R.id.btnCancelar);

        etNombre.setText(barbero.getNombre());
        btnCrear.setText("Actualizar");

        View dimBehind = new View(this);
        dimBehind.setBackgroundColor(0x88000000);
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().getRootView();
        rootView.addView(dimBehind, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        popupView.startAnimation(fadeIn);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> rootView.removeView(dimBehind));

        btnCrear.setOnClickListener(v -> {
            String nuevoNombre = etNombre.getText().toString().trim();
            if (!nuevoNombre.isEmpty()) {
                GestionarBarberoViewModel viewModel = new GestionarBarberoViewModel();
                viewModel.actualizarBarbero(this, barbero.getBarbero_id(), nuevoNombre, new GestionarBarberoViewModel.ActualizarCallback() {
                    @Override
                    public void onSuccess(String mensaje) {
                        Toast.makeText(GestionarBarberoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                        cargarLista();
                    }

                    @Override
                    public void onError(String mensaje) {
                        Toast.makeText(GestionarBarberoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                etNombre.setError("Campo obligatorio");
            }
        });

        btnCancelar.setOnClickListener(v -> popupWindow.dismiss());
    }


    private void mostrarPopupNuevoBarbero(View anchorView) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_nuevo_barbero, null);

        // Aplica animación fade-in (asegúrate de tener fade_in.xml en res/anim)
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        popupView.startAnimation(fadeIn);

        // Fondo opaco detrás
        View dimBehind = new View(this);
        dimBehind.setBackgroundColor(0x88000000); // semi-transparente oscuro
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().getRootView();
        rootView.addView(dimBehind, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> rootView.removeView(dimBehind));

        EditText etNombre = popupView.findViewById(R.id.etNombreNuevoBarbero);
        Button btnCrear = popupView.findViewById(R.id.btnCrearBarbero);
        Button btnCancelar = popupView.findViewById(R.id.btnCancelar);

        btnCrear.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            if (!nombre.isEmpty()) {
                crearNuevoBarbero(nombre); // ya definida en tu activity
                popupWindow.dismiss();
            } else {
                etNombre.setError("Campo obligatorio");
            }
        });

        btnCancelar.setOnClickListener(v -> popupWindow.dismiss());
    }


    private void crearNuevoBarbero(String nombre) {
        viewModel.crearBarbero(this, nombre, new GestionarBarberoViewModel.BarberoOperacionCallback() {
            @Override
            public void onSuccess(String mensaje) {
                Toast.makeText(GestionarBarberoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                cargarLista();
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(GestionarBarberoActivity.this, "Error: " + mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarBarbero(int id) {
        viewModel.eliminarBarbero(this, id, new GestionarBarberoViewModel.BarberoOperacionCallback() {
            @Override
            public void onSuccess(String mensaje) {
                Toast.makeText(GestionarBarberoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                cargarLista();
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(GestionarBarberoActivity.this, "Error: " + mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
