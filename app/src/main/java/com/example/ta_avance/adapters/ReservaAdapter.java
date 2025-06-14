package com.example.ta_avance.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ta_avance.R;
import com.example.ta_avance.dto.reserva.ReservaResponse;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private final List<ReservaResponse> listaReservas;
    private final OnReservaClickListener listener;
    private boolean mostrarBoton;

    public interface OnReservaClickListener {
        void onVerDetallesClick(ReservaResponse reserva);
    }

    public ReservaAdapter(List<ReservaResponse> listaReservas, OnReservaClickListener listener, boolean mostrarBoton) {
        this.listaReservas = listaReservas;
        this.listener = listener;
        this.mostrarBoton = mostrarBoton;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        ReservaResponse reserva = listaReservas.get(position);

        holder.tvUsuario.setText("Usuario: " + reserva.getUsuarioNombre());
        holder.tvBarbero.setText("Barbero: " + reserva.getBarberoNombre());
        holder.tvHorario.setText("Horario: " + reserva.getHorarioRango());
        holder.tvServicio.setText("Servicio: " + reserva.getServicioNombre());

        if (mostrarBoton) {
            holder.btnVerDetalles.setVisibility(View.VISIBLE);
            holder.btnVerDetalles.setOnClickListener(v -> listener.onVerDetallesClick(reserva));
        } else {
            holder.btnVerDetalles.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listaReservas.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsuario, tvBarbero, tvHorario, tvServicio;
        MaterialButton btnVerDetalles;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvUsuario);
            tvBarbero = itemView.findViewById(R.id.tvBarbero);
            tvHorario = itemView.findViewById(R.id.tvHorario);
            tvServicio = itemView.findViewById(R.id.tvServicio);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetallesReserva);
        }
    }


}
