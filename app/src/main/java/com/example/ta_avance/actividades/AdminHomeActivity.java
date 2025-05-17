package com.example.ta_avance.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
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
        setContentView(R.layout.activity_admin_home);

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
                startActivity(new Intent(this, GestionarServicioActivity.class)));

        findViewById(R.id.gestionHorarios).setOnClickListener(v -> {
            mostrarPopupHorarios(v);
        });

        findViewById(R.id.gestionBarbero).setOnClickListener(v ->{
                startActivity(new Intent(this,GestionarBarberoActivity.class));
        });

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

    private void mostrarPopupHorarios(View anchorView) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_horarios, null);

        // Aplica fade-in
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        popupView.startAnimation(fadeIn);

        // Fondo opaco
        View dimBehind = new View(this);
        dimBehind.setBackgroundColor(0x88000000); // semi-transparente oscuro
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().getRootView();
        rootView.addView(dimBehind, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        popupWindow.setOnDismissListener(() -> rootView.removeView(dimBehind));

        Button btnVerHorario = popupView.findViewById(R.id.btnVerHorario);
        Button btnPrepararHorario = popupView.findViewById(R.id.btnPrepararHorario);

        btnVerHorario.setOnClickListener(v -> {
            popupWindow.dismiss();
            Toast.makeText(this, "Ver horario actual", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HorarioActualActivity.class));
        });

        btnPrepararHorario.setOnClickListener(v -> {
            popupWindow.dismiss();
            Toast.makeText(this, "Preparar horario", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HorarioPrepararActivity.class));
        });
    }

}
