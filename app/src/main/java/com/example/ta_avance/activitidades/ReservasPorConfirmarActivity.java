package com.example.ta_avance.activitidades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ta_avance.R;

import androidx.appcompat.app.AppCompatActivity;

public class ReservasPorConfirmarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_por_confirmar);  // Asegúrate de tener el layout reservasporconfirmar.xml

        // Configurar las filas de la reserva 1
        findViewById(R.id.reservaRow1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el AlertDialog con la información de la reserva
                showReservationDetails("10:00 - 10:30", "Carlos", "Corte de cabello", "S/ 30");
            }
        });

        // Configurar las filas de la reserva 2
        findViewById(R.id.reservaRow2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el AlertDialog con la información de la reserva
                showReservationDetails("11:00 - 11:30", "Laura", "Afeitado", "S/ 20");
            }
        });
        Button btnVolverHome = findViewById(R.id.volverButton);
        btnVolverHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para redirigir a la actividad de Reservas
                Intent intent = new Intent(ReservasPorConfirmarActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para mostrar el AlertDialog con los detalles de la reserva
    public void showReservationDetails(String time, String barber, String service, String cost) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detalles de la Reserva");

        // Información de la reserva
        builder.setMessage("Fecha y Hora: " + time + "\n" +
                "Barbera: " + barber + "\n" +
                "Servicio: " + service + "\n" +
                "Costo: " + cost);

        // Botones de Confirmar y Cancelar
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para confirmar la reserva
                // Aquí puedes agregar el código para confirmar la reserva, si lo deseas.
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para cancelar la reserva
                // Aquí puedes agregar el código para cancelar la reserva, si lo deseas.
            }
        });

        // Mostrar el AlertDialog
        builder.create().show();
    }

}
