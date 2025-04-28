package com.example.ta_avance.activitidades;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.ta_avance.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ReservasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);  // Asegúrate de tener el layout reservas.xml
    }

    // Método para mostrar los detalles de los barberos que tienen citas a la hora seleccionada
    public void showBarbersAtTime(View view) {
        String timeSlot = ((Button)view).getText().toString();  // Obtiene el rango horario

        // Simulación de reservas en el rango horario
        String message = "";

        // Aquí puedes verificar qué barberos están ocupados en ese horario
        if (timeSlot.equals("9:00 - 9:30")) {
            message = "Reserva 1\nBarbera: Diego\nCliente: Cliente X\nServicio: Corte de cabello\nCosto: S/ 30\n\n";
            message += "Reserva 2\nBarbera: Laura\nCliente: Cliente Y\nServicio: Afeitado\nCosto: S/ 20";
        } else if (timeSlot.equals("9:30 - 10:00")) {
            message = "Reserva 1\nBarbera: Diego\nCliente: Cliente Z\nServicio: Afeitado\nCosto: S/ 20\n\n";
            message += "Reserva 2\nBarbera: Carlos\nCliente: Cliente W\nServicio: Corte de cabello\nCosto: S/ 30";
        } else {
            message = "No hay reservas en este horario.";
        }

        // Crear un AlertDialog para mostrar los detalles
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detalles de las Reservas");

        builder.setMessage(message);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ReservasActivity.this, "Reserva confirmada.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ReservasActivity.this, "Reserva cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        // Mostrar el Dialog
        builder.create().show();
    }
}
